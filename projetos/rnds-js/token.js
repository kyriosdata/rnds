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

function cnes(codigoCnes, callback) {
  function searchCnes(cnes, callback) {
    const path = "/api/fhir/r4/Organization/" + cnes;
    const bearer = "Bearer " + accessToken;
    console.log(path);
    //console.log(bearer);

    const options = {
      method: "GET",
      hostname: "ehr-services.hmg.saude.gov.br",
      path: path,
      headers: {
        "Content-Type": "application/json",
        "X-Authorization-Server": bearer,
        Authorization: requisitante,
      },
      maxRedirects: 20,
    };

    var req = https.request(options, function (res) {
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

  if (accessToken === undefined) {
    token(function () {
      searchCnes(codigoCnes, console.log);
    });
  } else {
    searchCnes(codigoCnes, console.log);
  }
}

//token(console.log);
cnes("2337991", console.log);
