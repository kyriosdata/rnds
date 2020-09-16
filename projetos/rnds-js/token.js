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
  function searchProfissional(cns, callback) {
    const options = {
      method: "GET",
      hostname: "ehr-services.hmg.saude.gov.br",
      path: "/api/fhir/r4/Practitioner/" + cns,
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
      searchProfissional(cns, callback);
    });
  } else {
    searchProfissional(cns, callback);
  }
}

//token(console.log);
//cnes("2337991", console.log);
//profissional(requisitante, console.log);

const options = {
  method: "GET",
  hostname: "ehr-services.hmg.saude.gov.br",
  path: "/api/fhir/r4/Practitioner/",
  maxRedirects: 20,
};

const securityAdded = {
  ...options,
  headers: {
    "Content-Type": "application/json",
    "X-Authorization-Server": "Bearer ",
    Authorization: requisitante,
  },
};

console.log(securityAdded.headers);
