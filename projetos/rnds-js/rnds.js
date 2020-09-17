const fs = require("fs");
const https = require("follow-redirects").https;

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

  buildRequest(options, (r) => {
    // Guarda em cache o access token para uso em chamadas posteriores
    accessToken = r.access_token;
    callback(r);
  });
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

/**
 * Recupera informações sobre profissional de saúde (via CPF).
 * @param {string} cpf CPF do profissional de saúde. Caso não fornecido,
 * será empregado o CPF do requisitante.
 * @param {function} callback Função a ser chamada com o retorno fornecido pela
 * RNDS.
 */
function cpf(cpf, callback) {
  if (arguments.length === 1) {
    callback = cpf;
    cns((json) => {
      const ids = json.identifier;
      const idx = ids.findIndex((i) => i.system.endsWith("/cpf"));
      cpf(ids[idx].value, callback);
    });

    return;
  }

  const options = {
    method: "GET",
    path:
      "/api/fhir/r4/Practitioner?identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" +
      cpf,
  };

  makeRequest(options, callback);
}

cpf(console.log);

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

function buildRequest(options, callback) {
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

//token(console.log);
//cnes("2337991", console.log);
//profissional(requisitante, (json) => {
//  const cpf = json.identifier[0].value;
//  profissionalPorCpf(cpf, (r) => console.log("Total de respostas:", r.total));
//});

// lotacao(requisitante, "2337991", console.log);
//cnpj("01567601000143", (r) => console.log("Organização:", r.name));

function makeRequest(options, callback) {
  // Se access_token não disponível, então tentar recuperar.
  if (accessToken === undefined) {
    token(function () {
      makeRequest(options, callback);
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

  buildRequest(securityAdded, callback);
}

module.exports = {
  cns: cns,
  cnes: cnes,
  cnpj: cnpj,
};
