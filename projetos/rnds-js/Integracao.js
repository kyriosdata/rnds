const fs = require("fs");
const https = require("follow-redirects").https;

/**
 * A resposta fornecida pela RNDS.
 * @typedef {Object} Resposta
 * @property {number} code - O código HTTP da resposta.
 * @property {Object} retorno - O objeto JavaScript correspondente ao JSON
 * retornado.
 * @property {Object} headers - Os headers retornados.
 */

/**
 * Envia requisição https conforme opções e, se for o caso,
 * com o payload indicado. É esperado que o retorno satisfatório
 * coincida com o código fornecido. Em caso de sucesso, a
 * callback é chamada com a resposta retornada (JavaScript object
 * correspondente ao JSON retornado) e, caso o código
 * esperado não seja o retornado, então instância de erro é retornada.
 *
 * @param {object} options Conjunto de propriedades que estabelecem a
 * configuração para a requisição.
 * @param {function} callback Função a ser chamada com três argumentos, na
 * seguinte ordem: (a) código de retorno; (b) o conteúdo retornado e
 * (c) headers retornados.
 * @param {string} payload Conteúdo a ser submetido. Se não fornecido,
 * então nada será enviado.
 */
function send(options, payload) {
  return new Promise((resolve, reject) => {
    const req = https.request(options, function (res) {
      const chunks = [];

      res.on("data", (chunk) => chunks.push(chunk));

      res.on("end", function (chunk) {
        const body = Buffer.concat(chunks);
        const json = body.length === 0 ? "" : JSON.parse(body.toString());

        // Repassado o código de retorno, o retorno e headers
        // (em vários cenários os headers não são relevantes)
        console.log("send received", res.statusCode);
        resolve({ code: res.statusCode, retorno: json, headers: res.headers });
      });

      res.on("error", function (error) {
        reject({
          msg: "Ocorreu um erro (requisição não envida executada",
          erro: error,
        });
      });
    });

    if (payload) {
      req.write(payload);
    }

    req.end();
  });
  // Se não fornecido ou vazio, não será enviado.
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
  token() {
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
      return send(options);
    } catch (err) {
      const error = new Error(
        `Não foi possível obter token.
       Certifique-se de que definiu corretamente 
       as variáveis de ambiente.
       Exceção: ${err}`
      );
      return Promise.reject(error);
    }
  }

  start() {
    return new Promise((resolve, reject) => {
      console.log("start called...");
      this.token().then((o) => {
        if (o.code === 200) {
          // Guarda em cache o access token para uso posterior
          this.access_token = o.retorno.access_token;
          console.log("access_token updated");
          resolve("ok");
        } else {
          this.access_token = undefined;
          reject("falha ao obter access_token");
        }
      });
    });
  }

  addSecurity(options) {
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

  /**
   * Constrói e executa requisição. Caso o retorno seja o código
   * 401 (unauthorized), automaticamente um novo token de acesso
   * é requisitado e, caso obtido, a requisição desejada é refeita.
   * Em consequência, se a "callback" for chamda com o código 401,
   * então tentou-se obter um novo token usando em uma nova tentativa,
   * antes de retornar tal código. Tal política contempla o caso de
   * token expirado.
   *
   * @param {object} options Opções que configuram a requisição.
   * @param {function} callback Função que será chamada com o código
   * de retorno, o retorno (JSON parsed) e headers.
   * @param {string} payload Mensagem ou conteúdo a ser enviado.
   */
  makeRequest(options, payload) {
    const envie = () => send(this.addSecurity(options), payload);

    const reenvieSeNaoAutorizado = (objeto) => {
      console.log("tentando novamente...");
      if (objeto.code !== 401) {
        console.log("retorno diferente de 401");
        return objeto;
      }

      return this.start().then(envie);
    };

    return this.getToken().then(envie).then(reenvieSeNaoAutorizado);
  }

  /**
   * Recupera informações sobre o estabelecimento de saúde.
   *
   * @param {string} cnes Código CNES do estabelecimento de saúde.
   * @returns {Promise<Resposta>} Promise que resulta na {@link Resposta} retornada.
   */
  cnes(cnes) {
    const options = {
      method: "GET",
      path: "/api/fhir/r4/Organization/" + cnes,
    };

    return this.makeRequest(options);
  }

  /**
   * Recupera informações sobre profissional de saúde (via CNS).
   * @param {string} cns Código CNS do profissional de saúde. Caso não
   * fornecido, será empregado o CNS do requisitante.
   * @returns {Promise<Resposta>}
   */
  cns(cns) {
    const profissional = cns ? cns : this.requisitante;
    const options = {
      method: "GET",
      path: "/api/fhir/r4/Practitioner/" + profissional,
    };

    return this.makeRequest(options);
  }

  /**
   * Recupera informações sobre profissional de saúde (via CPF).
   * @param {string} numero CPF do profissional de saúde. Caso não fornecido,
   * será empregado o CPF do requisitante.
   * @returns {Promise<Resposta>}
   */
  cpf(numero) {
    const options = {
      method: "GET",
      path:
        "/api/fhir/r4/Practitioner?identifier=http://rnds.saude.gov.br/fhir/r4/NamingSystem/cpf%7C" +
        numero,
    };
    return this.makeRequest(options);
  }

  getToken() {
    return this.access_token ? Promise.resolve() : this.start();
  }

  /**
   * Recupera informações sobre o paciante.
   *
   * @param {string} cpf O número do CPF do paciente.
   *
   * @returns {Promise<Resposta>}
   */
  paciente(cpf) {
    const options = {
      method: "GET",
      path:
        "/api/fhir/r4/Patient?" +
        "identifier=http://rnds.saude.gov.br/fhir/r4/NamingSystem/cpf%7C" +
        cpf,
    };

    return this.makeRequest(options);
  }

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
  notificar(payload) {
    const options = {
      method: "POST",
      path: "/api/fhir/r4/Bundle",
    };

    return this.makeRequest(options, payload);
  }
}

module.exports = RNDS;
const showError = (objeto) => console.log("ERRO", objeto);

const rnds = new RNDS();
//rnds.cnes("2337991").then(console.log).catch(showError);
//rnds.cns().then(console.log).catch(showError);
//rnds.cpf("9999").then(console.log).catch(showError);
//rnds.paciente("9999").then(console.log).catch(showError);
//rnds.notificar(fs.readFileSync("14.json")).then(console.log).catch(showError);
