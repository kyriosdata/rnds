import fs from "fs";

/**
 * Classe que implementa obtenção do token de acesso aos serviços
 * a RNDS usando a política de reutilização durante intervalo de
 * 30 minutos.
 */
export default class Token {
  /**
   * Construtor.
   * @param {Configuracao} configuracao Informa certificado, senha e serviço
   * de autenticação.
   * @param {*} security Habilita ou não segurança.
   * @param {function} send Função que retorna promise para requisição HTTP.
   * Esta função deve ser chamada com função a ser empregada para exibição
   * de informações, se for o caso.
   * @param {function} logging Função de logging a ser empregada, por
   * exemplo, console.log. Se não fornecida, não serão exibidas informações.
   */
  constructor(configuracao, security, send, logging) {
    this.log = logging || (() => {});
    this.access_token = undefined;

    if (!send) {
      throw new Error("função 'send' não fornecida");
    }

    // Define real função a ser utilizada, juntamente
    // com recurso para exibir informações, se for o caso.
    this.send = send(this.log);

    this.log("Token. Security is", security ? "ON" : "OFF");

    let pfx = undefined;
    try {
      pfx = fs.readFileSync(configuracao.certificado.valor);
    } catch (error) {
      throw new Error(
        `erro ao carregar arquivo pfx: ${configuracao.certificado.valor}`
      );
    }

    this.options = {
      method: "GET",
      path: "/api/token",
      headers: {},
      maxRedirects: 20,
      hostname: configuracao.auth.valor,
      pfx: pfx,
      passphrase: configuracao.senha.valor,
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

