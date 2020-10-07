const fs = require("fs");
const https = require("follow-redirects").https;

// TODO mensagem quando codigo de retorno é 500

// TODO criar classe
// 1. construtor verifica todas as vars
// 2. cache 'certificate' file (pfx)
// 3. instancia falha se não for possível obter token.

/**
 * Envia requisição https conforme opções e, se for o caso,
 * com o payload indicado. É esperado que o retorno satisfatório
 * coincida com o código fornecido. Em caso de sucesso, a
 * callback é chamada com a resposta retornada (JavaScript object
 * correspondente ao JSON retornado) e, caso o código
 * esperado não seja o retornado, então instância de erro é retornada.
 *
 * @param {*} options
 * @param {number} expectedCode Código de retorno esperado em uma execução
 * satisfatória.
 * @param {function} callback Função a ser chamada com dois argumentos, o
 * retorno obtido com a requisição e os headers.
 * @param {*} payload Conteúdo a ser submetido.
 */
function send(options, expectedCode, callback, payload) {
  const req = https.request(options, function (res) {
    var chunks = [];

    res.on("data", function (chunk) {
      chunks.push(chunk);
    });

    res.on("end", function (chunk) {
      const body = Buffer.concat(chunks);
      const corpo = body.toString();
      const json = corpo ? JSON.parse(corpo) : "";

      const payloadRetorno =
        res.statusCode != expectedCode
          ? new Error(
              `esperado ${expectedCode}, retornado ${res.statusCode}
              Resposta:
              ${corpo}`
            )
          : json;

      // Repassado o retorno e headers
      // (em vários cenários os headers não são relevantes)
      callback(payloadRetorno, res.headers);
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
}

/**
 * Variáveis de ambiente empregadas pelas funções.
 */

module.exports = RNDS;

new RNDS();
