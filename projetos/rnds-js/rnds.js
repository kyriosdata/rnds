const fs = require("fs");
const https = require("follow-redirects").https;

// TODO mensagem quando codigo de retorno é 500

// TODO criar classe
// 1. construtor verifica todas as vars
// 2. cache 'certificate' file (pfx)
// 3. instancia falha se não for possível obter token.

/**
 * Variáveis de ambiente empregadas pelas funções.
 */
const auth = process.env.RNDS_AUTH;
const ehr = process.env.RNDS_EHR;
const certificado = process.env.RNDS_CERTIFICADO_ENDERECO;
const senha = process.env.RNDS_CERTIFICADO_SENHA;
const requisitante = process.env.RNDS_REQUISITANTE_CNS;

/**
 * Cache access token
 */
let accessToken = undefined;

/**
 * Recupera <i>token</i> de acesso à RNDS por meio de um certificado
 * digital (arquivo <b>.pfx</b>).
 * @param {function} callback O <i>token</i> de acesso é recebido e passado
 * para esta função quando recuperado.
 */
function token(callback) {
  const options = {
    method: "GET",
    hostname: auth,
    path: "/api/token",
    headers: {},
    maxRedirects: 20,
    pfx: fs.readFileSync(certificado),
    passphrase: senha,
  };

  try {
    buildRequest(options, (r) => {
      // Guarda em cache o access token para uso em chamadas posteriores
      accessToken = r.access_token;
      callback(r);
    });
  } catch (err) {
    throw new Error(
      `Certifique-se de que definiu corretamente variáveis
       de ambiente, em particular, RNDS_CERTIFICADO_SENHA.
       Exceção: ${err}`
    );
  }
}

/**
 * Recupera informações sobre estabelecimento de saúde.
 *
 * @param {string} cnes Código CNES do estabelecimento de saúde.
 * @param {function} callback Função a ser chamada com o retorno fornecido pela RNDS.
 */
function cnes(cnes, callback) {
  const options = {
    method: "GET",
    hostname: ehr,
    path: "/api/fhir/r4/Organization/" + cnes,
  };

  makeRequest(options, callback);
}

/**
 * Recupera informações sobre profissional de saúde (via CNS).
 * @param {string} cns Código CNS do profissional de saúde. Caso não fornecido,
 * será empregado o CNS do requisitante.
 * @param {function} callback Função a ser chamada com o retorno fornecido pela
 * RNDS.
 */
function cns(cns, callback) {
  if (arguments.length === 1) {
    callback = cns;
    cns = requisitante;
  }

  const options = {
    method: "GET",
    path: "/api/fhir/r4/Practitioner/" + cns,
  };

  makeRequest(options, callback);
}

// cns("cns", (r) => console.log(r));

/**
 * Recupera informações sobre profissional de saúde (via CPF).
 * @param {string} numero CPF do profissional de saúde. Caso não fornecido,
 * será empregado o CPF do requisitante.
 * @param {function} callback Função a ser chamada com o retorno fornecido pela
 * RNDS.
 */
function cpf(numero, callback) {
  if (arguments.length === 1) {
    callback = numero;
    cns((json) => {
      const ids = json.identifier;
      const idx = ids.findIndex((i) => i.system.endsWith("/cpf"));
      cpf(ids[idx].value, callback);
    });
  } else {
    const options = {
      method: "GET",
      path:
        "/api/fhir/r4/Practitioner?identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" +
        numero,
    };

    makeRequest(options, callback);
  }
}

// cpf(console.log);

/**
 * Recupera informações sobre o paciante cujo CPF é fornecido.
 *
 * @param {string} numero O código (número) do CPF do paciente.
 *
 * @returns Informações sobre o paciente em um objeto JavaScript.
 */
function paciente(numero, callback) {
  const identifier =
    "identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" +
    numero;

  const options = {
    method: "GET",
    path: "/api/fhir/r4/Patient?" + identifier,
  };

  makeRequest(options, callback);
}

// paciente("cpf aqui", console.log);

/**
 * Notica o Ministério da Saúde, ou seja, submete resultado de
 * exame de COVID para a RNDS conforme padrão estabelecido. A
 * callback será chamada com o identificador único, gerado pela
 * RNDS, para fazer referência ao resultado submetido.
 *
 * @param {string} payload Resultado de exame devidamente empacotado
 * conforme perfil FHIR correspondente, definido pela RNDS.
 * @param {function} callback Função a ser chamada quando a submissão
 * for realizada de forma satisfatória. O argumento fornecido à função
 * será o identificador único, geraldo pela RNDS, para o resultado
 * submetido.
 */
function notificar(payload, callback) {
  const options = {
    method: "POST",
    path: "/api/fhir/r4/Bundle",
  };

  function encapsulada(resposta, headers) {
    const location = headers["location"];
    const rndsID = location.substring(location.lastIndexOf("/") + 1);
    callback(rndsID);
  }

  makeRequest(options, encapsulada, payload);
}

// notificar(fs.readFileSync("14.json", "utf-8"), console.log);

/**
 * Obtém o CNS (oficial) do paciente.
 *
 * @param {string} numero O número do CPF do paciente.
 * @param {function} callback A função chamada com o CNS do paciente.
 */
function cnsDoPaciente(numero, callback) {
  function cnsOficial(id) {
    return id.system.endsWith("/cns") && id.use === "official";
  }

  paciente(numero, (resultado) => {
    const ids = resultado.entry[0].resource.identifier;
    const idx = ids.findIndex((i) => cnsOficial(i));
    callback(ids[idx].value);
  });
}

// cnsDoPaciente("cpf do paciente", console.log);

function executeRequest(options, callback) {
  const req = https.request(options, function (res) {
    var chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function (chunk) {
      const body = Buffer.concat(chunks);
      const json = JSON.parse(body.toString());
      callback(json);
    });

    res.on("error", function (error) {
      console.error(error);
    });
  });

  req.end();
}

/**
 *
 * @param {*} options
 * @param {function} callback Função a ser chamada com dois argumentos, o
 * retorno obtido com a requisição e os headers.
 * @param {*} payload
 */
function buildRequest(options, callback, payload) {
  const req = https.request(options, function (res) {
    var chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function (chunk) {
      const body = Buffer.concat(chunks);
      const corpo = body.toString();
      const json = corpo ? JSON.parse(corpo) : "";
      callback(json, res.headers);
    });

    res.on("error", function (error) {
      console.error(error);
    });
  });

  if (payload) {
    console.log("PAYLOAD será enviado...");
    req.write(payload);
  } else {
    console.log("no payload");
  }

  req.end();
}

/**
 * Requisita informações sobre os papéis desempenhados por um profissional de saúde em um
 * dado estabelecimento em um período de tempo.
 *
 * @param {string} cns CNS do profissional de saúde
 * @param {string} cnes Código CNES do estabelecimento de saúde
 * @param {function} callback Função a ser chamada com a resposta para a lotação.
 */
function lotacao(cns, cnes, callback) {
  const practitioner =
    "practitioner.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" +
    cns;
  const organization =
    "organization.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" +
    cnes;

  const options = {
    method: "GET",
    path: "/api/fhir/r4/PractitionerRole?" + practitioner + "&" + organization,
  };

  makeRequest(options, callback);
}

function cnpj(cnpj, callback) {
  const options = {
    method: "GET",
    path: "/api/fhir/r4/Organization/" + cnpj,
  };

  makeRequest(options, callback);
}

// token(console.log);
// cnes("2337991", (r) => console.log(r));
//profissional(requisitante, (json) => {
//  const cpf = json.identifier[0].value;
//  profissionalPorCpf(cpf, (r) => console.log("Total de respostas:", r.total));
//});

// lotacao(requisitante, "2337991", (r) => console.log(r));
//cnpj("01567601000143", (r) => console.log("Organização:", r.name));

function makeRequest(options, callback, payload) {
  // Se access_token não disponível, então tentar recuperar.
  if (accessToken === undefined) {
    token(function () {
      makeRequest(options, callback, payload);
    });

    return;
  }

  // Token de acesso agora está disponível
  const securityAdded = {
    ...options,
    hostname: ehr,
    headers: {
      "Content-Type": "application/json",
      "X-Authorization-Server": "Bearer " + accessToken,
      Authorization: requisitante,
    },
    maxRedirects: 10,
  };

  buildRequest(securityAdded, callback, payload);
}

module.exports = {
  cns: cns,
  cpf: cpf,
  cnes: cnes,
  cnpj: cnpj,
  paciente: paciente,
  cnsDoPaciente: cnsDoPaciente,
  notificar: notificar,
};
