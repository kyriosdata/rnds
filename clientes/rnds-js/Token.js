const fs = require("fs");
const { config } = require("process");

/**
 * Classe que oferece token de acesso aos serviços da RNDS usando
 * a política de reutilização durante intervalo de 30 minutos.
 */
class Token {
  /**
   * Construtor.
   * @param {function} logging Função de logging a ser empregada, por
   * exemplo, console.log.
   * @param {Configuracao} configuracao Informa certificado, senha e serviço
   * de autenticação.
   * @param {*} security Habilita ou não segurança.
   * @param {function} send Função que retorna promise para requisição HTTP.
   */
  constructor(logging, configuracao, security, send) {
    this.log = logging;
    this.access_token = undefined;
    this.send = send;

    this.log("Criando serviço de acesso a token.");
    this.log("Security is", security ? "ON" : "OFF");
    this.log("RNDS_AUTH", configuracao.auth);
    this.log("RNDS_CERTIFICADO_ENDERECO", configuracao.certificado);
    this.log("RNDS_CERTIFICADO_SENHA", "omitida por segurança");

    let pfx = undefined;
    try {
      pfx = fs.readFileSync(configuracao.certificado);
    } catch (error) {
      throw new Error(
        `erro ao carregar arquivo pfx: ${configuracao.certificado}`
      );
    }

    this.options = {
      method: "GET",
      path: "/api/token",
      headers: {},
      maxRedirects: 20,
      hostname: configuracao.auth,
      pfx: pfx,
      passphrase: configuracao.senha,
    };

    /**
     * Se segurança não está habilitada, simplesmente
     * resolve.
     */
    function securityDisabled() {
      return Promise.resolve("access_token not used");
    }

    /**
     * Se segurança está habilitada, na disponibilidade de token,
     * simplesmente retorne-o. Se o token não está definido,
     * então renove-o.
     */
    function securityEnabled() {
      return this.access_token
        ? Promise.resolve(this.access_token)
        : this.renoveAccessToken();
    }

    /**
     * Recupera token.
     */
    this.getToken = security ? securityEnabled : securityDisabled;
  }

  limpaToken() {
    this.log("access_token cleared...");
    this.access_token = undefined;
  }

  /**
   * Obtém e armazena em cache o access token a ser empregado
   * para requisições à RNDS.
   */
  renoveAccessToken() {
    /**
     * Recupera <i>token</i> de acesso à RNDS.
     *
     * @returns {Promise<Resposta>}
     */
    const requestAccessToken = () => {
      try {
        return this.send(this.options);
      } catch (err) {
        const error = new Error(
          `Não foi possível obter token. Verifique variáveis de ambiente.
          Exceção: ${err}`
        );
        return Promise.reject(error);
      }
    };

    return requestAccessToken()
      .then((o) => {
        if (o.code === 200) {
          // Guarda em cache o access token para uso posterior
          this.access_token = JSON.parse(o.retorno).access_token;
          this.log("access_token redefinido");
          return Promise.resolve(this.access_token);
        } else {
          this.access_token = undefined;
          return Promise.reject("falha ao obter access_token");
        }
      })
      .catch(() => Promise.reject("senha inválida (erro ao obter token)"));
  }
}

module.exports = Token;
