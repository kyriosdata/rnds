const fs = require("fs");
const https = require("follow-redirects").https;

/**
 * Envia requisição https conforme opções e, se for o caso,
 * com o payload indicado. É esperado que o retorno satisfatório
 * coincida com o código fornecido. Em caso de sucesso, a
 * callback é chamada com a resposta retornada (JavaScript object
 * correspondente ao JSON retornado) e, caso o código
 * esperado não seja o retornado, então instância de erro é retornada.
 *
 * @param {*} options
 * @param {function} callback Função a ser chamada com três argumentos, na
 * seguinte ordem: (a) código de retorno; (b) o conteúdo retornado e
 * (c) headers retornados.
 * @param {*} payload Conteúdo a ser submetido.
 */
function send(options, callback, payload) {
  const req = https.request(options, function (res) {
    var chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function (chunk) {
      const body = Buffer.concat(chunks);
      const corpo = body.toString();
      const json = corpo ? JSON.parse(corpo) : "";

      // Repassado o código de retorno, o retorno e headers
      // (em vários cenários os headers não são relevantes)
      callback(res.statusCode, json, res.headers);
    });

    res.on("error", function (error) {
      console.log("Ocorreu um erro (requisição não envida satisfatoriamente");
      console.error(error);
    });
  });

  // Se payload fornecido, este deve ser enviado.
  if (payload) {
    req.write(payload);
  }

  req.end();
}

class RNDS {
  constructor() {
    function checkVariable(nome, valor) {
      if (!valor || valor.length === 0) {
        throw new Error(`variavel ${nome} não definida ou vazia`);
      }
    }

    this.auth = process.env.RNDS_AUTH;
    this.ehr = process.env.RNDS_EHR;
    this.certificado = process.env.RNDS_CERTIFICADO_ENDERECO;
    this.senha = process.env.RNDS_CERTIFICADO_SENHA;
    this.requisitante = process.env.RNDS_REQUISITANTE_CNS;
    this.access_token = undefined;

    checkVariable("RNDS_AUTH", this.auth);
    checkVariable("RNDS_EHR", this.ehr);
    checkVariable("RNDS_CERTIFICADO_ENDERECO", this.certificado);
    checkVariable("RNDS_CERTIFICADO_SENHA", this.senha);
    checkVariable("RNDS_REQUISITANTE_CNS", this.requisitante);

    // Cache certificate
    try {
      this.pfx = fs.readFileSync(this.certificado);
    } catch (error) {
      throw new Error(`erro ao carregar arquivo pfx: ${this.certificado}`);
    }
  }

  /**
   * Recupera <i>token</i> de acesso à RNDS.
   * @param {function} callback O <i>token</i> de acesso é recebido e passado
   * para esta função quando recuperado. Esta função recebe três argumentos:
   * (a) código de retorno; (b) retorno e (c) headers retornados pela
   * execução da requisição.
   */
  token(callback) {
    console.log("token() gets called...");
    try {
      const options = {
        method: "GET",
        path: "/api/token",
        headers: {},
        maxRedirects: 20,
        hostname: this.auth,
        pfx: this.pfx,
        passphrase: this.senha,
      };
      send(options, callback);
    } catch (err) {
      const error = new Error(
        `Não foi possível obter token.
       Certifique-se de que definiu corretamente 
       as variáveis de ambiente.
       Exceção: ${err}`
      );
      callback(-1, error);
    }
  }

  start() {
    return new Promise((resolve, reject) => {
      this.token((c, r) => {
        if (c === 200) {
          // Guarda em cache o access token para uso posterior
          this.access_token = r.access_token;
          resolve("ok");
        } else {
          this.access_token = undefined;
          reject("falha ao obter access_token");
        }
      });
    });
  }

  addSecurityToOptions(options) {
    return {
      ...options,
      hostname: this.ehr,
      headers: {
        "Content-Type": "application/json",
        "X-Authorization-Server": "Bearer " + this.access_token,
        Authorization: this.requisitante,
      },
      maxRedirects: 10,
    };
  }

  whatMustBeDone(options, callback, payload) {
    const securityAdded = this.addSecurityToOptions(options);
    send(securityAdded, callback, payload);
  }

  makeRequest(options, callback, payload) {
    console.log("makeRequest called");

    // Se access_token não disponível, então tentar recuperar.
    if (this.access_token === undefined) {
      this.start()
        .then(() => this.whatMustBeDone(options, callback, payload))
        .catch((v) => console.log(v));
    } else {
      console.log("já iniciado...");
      this.whatMustBeDone(options, callback, payload);
    }
  }

  /**
   * Recupera informações sobre estabelecimento de saúde.
   *
   * @param {string} cnes Código CNES do estabelecimento de saúde.
   * @param {function} callback Função a ser chamada com o retorno fornecido pela RNDS.
   */
  cnes(cnes, callback) {
    console.log("cnes called");
    const options = {
      method: "GET",
      path: "/api/fhir/r4/Organization/" + cnes,
    };

    this.makeRequest(options, callback);
  }

  getToken() {
    return this.access_token;
  }
}

module.exports = RNDS;

const rnds = new RNDS();
//rnds.start().then(() => console.log("fim"));
rnds
  .start()
  .then(() => rnds.cnes("2337991", console.log))
  .catch((e) => console.log(e));

//rnds.cnes("2337991", console.log);
