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
  constructor(configuracao, security, send, logging = () => {}) {
    this.log = logging;
    this.access_token = undefined;

    if (!send) {
      throw new Error("função 'send' não fornecida");
    }

    // Define função usada para requisições
    this.send = send(this.log);

    this.log("Token. Security is", security ? "ON" : "OFF");

    this.options = {
      method: "GET",
      path: "/api/token",
      headers: {
        "User-Agent": "RNDS Library",
      },
      maxRedirects: 20,
      host: configuracao.auth.valor,
      pfx: configuracao.pfx.valor,
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
     * Define função para recuperar o token conforme configuração de segurança.
     */
    this.getToken = security ? securityEnabled : securityDisabled;
  }

  limpaToken() {
    this.log("access_token cleared...");
    this.access_token = undefined;
  }

  /**
   * Obtém e armazena o access token.
   */
  async renoveAccessToken() {
    try {
      const resposta = await this.send(this.options);
      if (resposta.code === 200) {
        this.access_token = JSON.parse(resposta.retorno).access_token;
        this.log("access_token redefinido");
        return Promise.resolve(this.access_token);
      } else {
        return Promise.reject(resposta);
      }
    } catch (erro) {
      return Promise.reject(erro);
    }
  }
}

