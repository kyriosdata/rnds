const fs = require("fs");
const https = require("https");

const auth = process.env.RNDS_AUTH;
const ehr = process.env.RNDS_EHR;
const certificado = process.env.RNDS_CERTIFICADO_ENDERECO;
const senha = process.env.RNDS_CERTIFICADO_SENHA;
const requisitante = process.env.RNDS_REQUISITANTE_CNS;

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
  const req = https.request(
    {
      hostname: auth,
      path: "/api/token",
      method: "GET",
      pfx: fs.readFileSync(certificado),
      passphrase: senha,
    },
    (res) => {
      res.on("data", function (data) {
        console.log("recebido");
        const jwt = JSON.parse(data.toString());
        callback(jwt.access_token);
      });
    }
  );

  req.end();
}

token(console.log);
