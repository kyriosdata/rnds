const fs = require("fs");
const https = require("follow-redirects").https;

/**
 * A resposta fornecida pela RNDS, conforme recebida, para a requisição
 * submetida. O código de retorno é fornecido pela propriedade
 * code. O payload pela propriedade retorno e, pela propriedade headers,
 * todos os cabeçalhos fornecidos pela RNDS.
 *
 * @typedef {Object} Resposta
 * @property {number} code - O código HTTP da resposta.
 * @property {Object} retorno - O payload retornado pela RNDS.
 * @property {Object} headers - Os headers retornados.
 */

function configuracao() {
  return {
    auth: process.env.RNDS_AUTH,
    certificado: process.env.RNDS_CERTIFICADO_ENDERECO,
    senha: process.env.RNDS_CERTIFICADO_SENHA,
    ehr: process.env.RNDS_EHR,
    requisitante: process.env.RNDS_REQUISITANTE_CNS,
  };
}

/**
 * Classe que oferece acesso aos serviços oferecidos pela RNDS.
 */
class RNDS {
  /**
   * Cria uma instância necessária para a conexão com o ambiente de
   * homologação ou produção da RNDS.
   *
   * @param {boolean} logging O valor true para habilitar o logging ou
   * false, caso contrário.
   *
   * @param {boolean} noSecurity O valor true para indicar que token de
   * acesso não deve ser empregado (valor padrão) ou false,
   * para empregar token de acesso.
   */
  constructor(logging, noSecurity) {
    this.logging = !!logging;
    this.noSecurity = !!noSecurity;

    const { auth, certificado, senha, ehr, requisitante } = configuracao();

    const seguranca = () => {
      this.auth = auth;
      this.certificado = certificado;
      this.senha = senha;
      this.access_token = undefined;

      check("RNDS_AUTH", this.auth);
      check("RNDS_CERTIFICADO_ENDERECO", this.certificado);
      check("RNDS_CERTIFICADO_SENHA", this.senha);

      // Cache certificate
      try {
        this.pfx = fs.readFileSync(this.certificado);
      } catch (error) {
        throw new Error(`erro ao carregar arquivo pfx: ${this.certificado}`);
      }

      this.log("RNDS_AUTH", this.auth);
      this.log("RNDS_CERTIFICADO_ENDERECO", this.certificado);
      this.log("RNDS_CERTIFICADO_SENHA", "omitida por segurança");
    };

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
     *
     * @param {function} callback Função a ser chamada com três argumentos, na
     * seguinte ordem: (a) código de retorno; (b) o conteúdo retornado e
     * (c) headers retornados.
     *
     * @param {string} payload Conteúdo a ser submetido. Se não fornecido,
     * então nada será enviado.
     *
     * @returns {Promise<Resposta>} Promise para a resposta obtida pela
     * execução da requisição.
     */
    this.send = function (options, payload) {
      this.log(options.method, options.path);

      return new Promise((resolve, reject) => {
        const innerLog = this.log;
        const req = https.request(options, function (res) {
          const chunks = [];

          res.on("data", (chunk) => chunks.push(chunk));

          res.on("end", function (chunk) {
            const body = Buffer.concat(chunks);
            const json = body.length === 0 ? "" : body.toString();

            // Repassado o código de retorno, o retorno e headers
            // (em vários cenários os headers não são relevantes)
            innerLog(options.path, res.statusCode);
            resolve({
              code: res.statusCode,
              retorno: json,
              headers: res.headers,
            });
          });

          res.on("error", function (error) {
            reject({
              msg: "Ocorreu um erro (requisição não envida executada",
              erro: error,
            });
          });
        });

        // Se não fornecido ou vazio, não será enviado.
        if (payload) {
          req.write(payload);
        }

        req.end();
      });
    };

    this.log = (p, s) => {
      if (this.logging) console.log("RNDS:", p, s || "");
    };

    function check(nome, valor) {
      if (!valor || valor.length === 0) {
        throw new Error(`variavel ${nome} não definida ou vazia`);
      }
    }

    this.ehr = ehr;
    this.requisitante = requisitante;

    check("RNDS_EHR", this.ehr);
    check("RNDS_REQUISITANTE_CNS", this.requisitante);

    this.log("RNDS_EHR", this.ehr);
    this.log("RNDS_REQUISITANTE_CNS", this.requisitante);

    // ATRIBUTOS EXIGIDOS APENAS SE useToken É VERDADEIRO
    if (noSecurity) {
      return;
    }

    seguranca();
  }

  /**
   * Recupera <i>token</i> de acesso à RNDS.
   * @param {function} callback O <i>token</i> de acesso é recebido e passado
   * para esta função quando recuperado. Esta função recebe três argumentos:
   * (a) código de retorno; (b) retorno e (c) headers retornados pela
   * execução da requisição.
   *
   * @returns {Promise<Resposta>}
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
      return this.send(options);
    } catch (err) {
      const error = new Error(
        `Não foi possível obter token. Verifique variáveis de ambiente.
        Exceção: ${err}`
      );
      return Promise.reject(error);
    }
  }

  /**
   * Obtém e armazena em cache o access token a ser empregado
   * para requisições à RNDS.
   */
  iniciar() {
    if (this.noSecurity) {
      return Promise.resolve("undefined");
    }

    return this.token().then((o) => {
      if (o.code === 200) {
        // Guarda em cache o access token para uso posterior
        this.access_token = JSON.parse(o.retorno).access_token;
        this.log("access_token redefinido");
        return Promise.resolve("ok");
      } else {
        this.access_token = undefined;
        return Promise.reject("falha ao obter access_token");
      }
    });
  }

  /**
   * Obtém o access token. O token é requisitado ao serviço
   * correspondente se não estiver disponível no cache.
   */
  getToken() {
    this.log("getToken()");
    return this.access_token ? Promise.resolve() : this.iniciar();
  }

  /**
   * Preenche configuração de requisição com elementos comuns
   * às requisições: (a) hostname; (b) Content-Type;
   * (c) X-Authorization-Server" e (d) Authorization.
   *
   * @param {Object} options Opções empregadas para configurar
   * requisição https.
   *
   * @returns Objeto fornecido e acrescido das propriedades
   * descritas acima.
   */
  inflar(options) {
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
   * Constrói e submete uma requisição para a RNDS.
   * Se a requisição falha, ou seja, recebe o código
   * 401 (unauthorized) ou 502 (bad gateway), automaticamente
   * um novo token de acesso é requisitado e uma nova
   * tentativa é realizada. Nesta segunda tentativa,
   * o resultado, qualquer que seja ele, será retornado.
   *
   * @param {object} options Opções que configuram a requisição.
   * @param {function} callback Função que será chamada com o código
   * de retorno, o retorno (JSON parsed) e headers.
   * @param {string} payload Mensagem ou conteúdo a ser enviado.
   */
  makeRequest(options, payload) {
    const envie = () => this.send(this.inflar(options), payload);

    const reenvieSeFalha = (objeto) => {
      // Se falha de autenticacao ou bad gateway (aws)
      if (objeto.code === 401 || objeto.code === 502) {
        this.log(`recebido ${objeto.code}, tentar com novo token...`);
        return this.iniciar().then(envie);
      }

      return objeto;
    };

    return this.getToken().then(envie).then(reenvieSeFalha);
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
   * Recupera informações sobre profissional de saúde.
   * @param {string} numero CPF do profissional de saúde.
   * @returns {Promise<Resposta>} Caso o CPF seja inválido ou
   * não esteja associado a um profissional de saúde, será necessário
   * verificar a propriedade "retorno" (payload) para identificar
   * que profissional não foi encontrado.
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

  /**
   * Recupera informações sobre pessoa jurídica.
   *
   * @param {string} cnpj Número do CNPJ da pessoa jurídica.
   * @returns {Promise<Resposta>}
   */
  cnpj(cnpj) {
    const options = {
      method: "GET",
      path: "/api/fhir/r4/Organization/" + cnpj,
    };

    return this.makeRequest(options);
  }

  /**
   * Recupera informações sobre profissional liberal pelo CNES ou
   * CPF do profissional em questão.
   *
   * @param {string} cpfOuCnes Número do CPF do profissional liberal ou
   * CNES.
   *
   * @returns {Promise<Resposta>}
   */
  profissionalLiberal(cpfOuCnes) {
    return this.cnes(cpfOuCnes);
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
   * @returns {Promise<Resposta>}
   */
  notificar(payload) {
    /**
     * Extrai da resposta ao resultado de exame submetido o
     * identificador atribuído pela RNDS à resposta.
     *
     * @param {Resposta} resposta A resposta recebida para o
     * resultado submetido.
     *
     * @returns O objeto recebido onde a propriedade "retorno" recebe
     * o valor do identificador do resultado de exame atribuído pela
     * RNDS. Em caso de resposta com código diferente de 201, então a
     * resposta recebida é retornada.
     */
    function extraiRndsId(resposta) {
      if (resposta.code !== 201) {
        return resposta;
      }

      const location = resposta.headers["location"];
      const rndsId = location.substring(location.lastIndexOf("/") + 1);
      resposta.retorno = rndsId;
      return resposta;
    }

    const options = {
      method: "POST",
      path: "/api/fhir/r4/Bundle",
    };

    return this.makeRequest(options, payload).then(extraiRndsId);
  }

  /**
   * Substitui resultado de exame submetido anteriormente.
   *
   * @param {string} payload Resultado de exame devidamente empacotado
   * conforme perfil FHIR correspondente, definido pela RNDS, para
   * substituir resultado previamente submetido.
   *
   * @returns {Promise<Resposta>}
   */
  substituir(payload) {
    // TODO verificar presença de "relatesTo"?
    return this.notificar(payload);
  }

  /**
   * Obtém o CNS (oficial) do paciente.
   *
   * @param {string} numero O número do CPF do paciente.
   * @returns {Promise<Resposta>} A propriedade "retorno" da
   * Resposta contém o CNS do paciente correspondente ao
   * CPF fornecido. Certifique-se de que o código de retorno (code)
   * possui o valor 200.
   */
  cnsDoPaciente(numero) {
    function cnsOficial(id) {
      return id.system.endsWith("/cns") && id.use === "official";
    }

    const extraiCnsSeEncontrado = (resposta) => {
      if (resposta.code !== 200) {
        return resposta;
      }

      const json = JSON.parse(resposta.retorno);

      // Se não encontrado
      if (json.total === 0) {
        return { ...resposta, retorno: "" };
      }

      const ids = json.entry[0].resource.identifier;
      const idx = ids.findIndex((i) => cnsOficial(i));
      return { ...resposta, retorno: ids[idx].value };
    };

    return this.paciente(numero).then(extraiCnsSeEncontrado);
  }

  /**
   * Requisita informações sobre os papéis desempenhados por um profissional de saúde em um
   * dado estabelecimento em um período de tempo.
   *
   * @param {string} cns CNS do profissional de saúde
   * @param {string} cnes Código CNES do estabelecimento de saúde
   * @returns {Promise<Resposta>}
   */
  lotacao(cns, cnes) {
    const practitioner =
      "practitioner.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" +
      cns;
    const organization =
      "organization.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" +
      cnes;

    const options = {
      method: "GET",
      path:
        "/api/fhir/r4/PractitionerRole?" + practitioner + "&" + organization,
    };

    return this.makeRequest(options);
  }

  /**
   * Recupera "token" de acesso à informação do usuário.
   *
   * @param {string} cnes CNES do estabelecimento de saúde.
   * @param {string} cnsProfissional CNS do profissional.
   * @param {string} cnsPaciente CNS do paciente.
   * @returns {Promise<Resposta>} O token que permite acesso a
   * informações do paciente é retornada na propriedade
   * "retorno". Observe que a propriedade "code" deve possuir o valor
   * 200.
   */
  contextoAtendimento(cnes, cnsProfissional, cnsPaciente) {
    const options = {
      method: "POST",
      path: "/api/contexto-atendimento",
    };

    const payload = { cnes, cnsProfissional, cnsPaciente };
    return this.makeRequest(options, JSON.stringify(payload));
  }
}

module.exports = RNDS;

const rnds = new RNDS(true, false);
rnds.paciente("x").then(console.log);
