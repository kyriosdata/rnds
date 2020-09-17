const fs = require("fs");
const https = require("follow-redirects").https;

const auth = process.env.RNDS_AUTH;
const ehr = process.env.RNDS_EHR;
const certificado = process.env.RNDS_CERTIFICADO_ENDERECO;
const senha = process.env.RNDS_CERTIFICADO_SENHA;
const requisitante = process.env.RNDS_REQUISITANTE_CNS;

/**
 * Cache access toke)
 */
let accessToken = undefined;

console.log(auth);
console.log(ehr);
console.log(certificado);
console.log(senha);
console.log(requisitante);

//console.log(fs.readFileSync(certificado).length);

/**
 * Recupera <i>token</i> de acesso.
 * @param {function} callback O <i>token</i> de acesso é recebido e passado
 * para esta função quando recuperado.
 */
function token(callback) {
  if (accessToken !== undefined) {
    return callback(accessToken);
  }

  const options = {
    method: "GET",
    hostname: "ehr-auth-hmg.saude.gov.br",
    path: "/api/token",
    headers: {},
    maxRedirects: 20,
    pfx: fs.readFileSync(certificado),
    passphrase: senha,
  };

  const req = https.request(options, function (res) {
    const chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function (chunk) {
      const body = Buffer.concat(chunks);
      const resposta = JSON.parse(body.toString());
      accessToken = resposta.access_token;
      callback(accessToken);
    });

    res.on("error", function (error) {
      console.error(error);
    });
  });

  req.end();
}

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

function cnes(codigoCnes, callback) {
  function searchCnes(cnes, callback) {
    const options = {
      method: "GET",
      hostname: "ehr-services.hmg.saude.gov.br",
      path: "/api/fhir/r4/Organization/" + cnes,
      headers: {
        "Content-Type": "application/json",
        "X-Authorization-Server": "Bearer " + accessToken,
        Authorization: requisitante,
      },
      maxRedirects: 20,
    };

    executeRequest(options, callback);
  }

  if (accessToken === undefined) {
    token(function () {
      searchCnes(codigoCnes, callback);
    });
  } else {
    searchCnes(codigoCnes, callback);
  }
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

function profissional(cns, callback) {
  const options = {
    method: "GET",
    path: "/api/fhir/r4/Practitioner/" + cns,
  };

  makeRequest(options, callback);
}

function profissionalPorCpf(cpf, callback) {
  const options = {
    method: "GET",
    path:
      "/api/fhir/r4/Practitioner?identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" +
      cpf,
  };

  makeRequest(options, callback);
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
cnpj("01567601000143", (r) => console.log("Organização:", r.name));

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
