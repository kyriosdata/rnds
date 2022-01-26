import {obtemConfiguracao, getVersao} from "./configuracao.js";

console.log("rnds-js", getVersao());

import Token from "./Token.js";
import {sendService} from "./send.js";

/**
 * Versão contemplada pelo presente cliente.
 * Apenas servidor que implementa esta versão
 * será considerado.
 */
const FHIR_VERSION = "4.0.1";

/**
 * A resposta obtida da execução de requisição à RNDS.
 * O código de retorno é fornecido pela propriedade
 * code. O payload retornado pela RNDS é fornecido na
 * propriedade retorno e, pela propriedade headers,
 * todos os cabeçalhos fornecidos pelo servidor FHIR
 * da RNDS.
 *
 * @typedef {Object} Resposta
 * @property {number} code - O código HTTP da resposta.
 * @property {Object} retorno - O payload retornado pela RNDS.
 * @property {Object} headers - Os headers retornados.
 */

/**
 * Funcão que retorna função a ser empregada para logging, ou seja,
 * console.log ou função vazia (sem efeito).
 *
 * @param {boolean} logging Verdadeiro para habilitar logging via console.log ou false para desabilitar.
 * @returns {function} Função empregada para logging, possivelmente vazia, sem efeito, caso o argumento fornecido seja false.
 */
function log(logging) {
    if (logging) {
        console.log("logging habilitado");
        return (p, s) => console.log("RNDS:", p, s || "");
    } else {
        return () => {
        };
    }
}

/**
 * Classe que oferece acesso aos serviços oferecidos pela RNDS.
 * É necessário criar uma instância desta classe para requisição
 * de qualquer serviço à RNDS.
 */
export default class RNDS {
    /**
     * Cria uma instância necessária para a conexão com o ambiente de
     * homologação ou produção da RNDS.
     *
     * @param {boolean} logging O valor true para habilitar o logging ou
     * false, caso contrário.
     *
     * @param {boolean} security O valor false para indicar que token de
     * acesso não deve ser empregado ou true,
     * para empregar token de acesso.
     */
    constructor(logging, security) {
        this.log = log(logging);

        // Mantém todas as informações de configuração de acesso
        this.cfg = obtemConfiguracao();

        this.send = sendService(this.log);

        this.log("RNDS_EHR", this.cfg.ehr.valor);
        this.log("RNDS_REQUISITANTE_CNS", this.cfg.requisitante.valor);

        this.cache = new Token(this.cfg, security, sendService, this.log);
    }

    /**
     * Cria uma instância necessária para a conexão com o ambiente de
     * homologação ou produção da RNDS.
     *
     * @param {boolean} logging O valor true para habilitar o logging ou
     * false, caso contrário.
     *
     * @param {boolean} security O valor false para indicar que token de
     * acesso não deve ser empregado ou true,
     * para empregar token de acesso.
     *
     * @param {boolean} check O valor deve ser verdadeiro para indicar
     * que a verificação de versão deve ser realizada. Se o servidor empregar
     * versão diferente do cliente, então gera exceção. Se falso, então
     * esta verificação não é realizada.
     */
    static async cliente(logging, security, check) {
        if (typeof logging !== "boolean"
            || typeof security !== "boolean"
            || typeof check !== "boolean") {
            throw new Error("logging, security e check são obrigatórios");
        }

        const rnds = new RNDS(logging, security);

        if (check === false) {
            return rnds;
        }

        try {
            const verificaToken = await rnds.verifica();
            const verificaVersao = await rnds.checkVersion();

            if (!verificaToken || !verificaVersao) {
                throw Error;
            }
        } catch (error) {
            throw new Error("Versão de servidor FHIR não validada.");
        }

        return rnds;
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
     *
     */
    inflar(options) {
        return {
            method: "GET",
            hostname: this.cfg.ehr.valor,
            headers: {
                "User-Agent": "RNDS Library",
                "Content-Type": "application/json",
                "X-Authorization-Server": "Bearer " + this.cache.access_token,
                Authorization: this.cfg.requisitante.valor,
            },
            maxRedirects: 10,
            ...options,
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
                this.cache.limpaToken();
                return this.cache.getToken().then(envie);
            }

            return objeto;
        };

        return this.cache.getToken().then(envie).then(reenvieSeFalha);
    }

    /**
     * Recupera as capacidades oferecidas pelo
     * servidor FHIR disponibilizado pela RNDS.
     *
     * @returns {Promise<Resposta>}
     */
    async capability() {
        const options = {
            path: "/api/fhir/r4/metadata",
        };

        return this.makeRequest(options);
    }

    /**
     * Verifica compatibilidade entre a versão do servidor e o cliente.
     *
     * @returns true se a versão do cliente é compatível com aquela do
     * servidor.
     */
    async checkVersion() {
        let resposta;

        try {
            resposta = await this.capability();
            if (resposta.code !== 200) {
                return false;
            }
        } catch (error) {
            return false;
        }

        const json = JSON.parse(resposta.retorno);
        return json.fhirVersion === FHIR_VERSION;
    }

    /**
     * Recupera "token" de acesso à informação do usuário.
     *
     * @param {string} cnes CNES do estabelecimento de saúde.
     * @param {string} cnsProfissional CNS do profissional.
     * @param {string} cnsPaciente CNS do paciente.
     * @returns {Promise<Resposta>} O token que permite acesso a
     * informações do paciente é retornada na propriedade
     * "retorno". Observe que a propriedade ciente.
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

        const payload = {cnes, cnsProfissional, cnsPaciente};
        return this.makeRequest(options, JSON.stringify(payload));
    }

    /**
     * Recupera informações sobre o estabelecimento de saúde associado
     * ao código CNES.
     *
     * @param {string} cnes Código CNES do estabelecimento de saúde.
     * @returns {Promise<Resposta>} Promise que resulta na {@link Resposta} retornada.
     */
    cnes(cnes) {
        const options = {
            path: "/api/fhir/r4/Organization/" + cnes,
        };

        return this.makeRequest(options);
    }

    /**
     * Recupera informações sobre o profissional de saúde pelo seu
     * código CNS.
     *
     * @param {string} cns Código CNS do profissional de saúde. Caso não
     * fornecido, será empregado o CNS do requisitante.
     * 
     * @returns {Promise<Resposta>} A {@link Resposta} produzida pela RNDS.
     */
    cns(cns) {
        const options = {
            path: "/api/fhir/r4/Practitioner/" + cns,
        };

        return this.makeRequest(options);
    }

    /**
     * Recupera informações sobre profissional de saúde pelo CPF.
     * @param {string} numero CPF do profissional de saúde.
     * @returns {Promise<Resposta>} Caso o CPF seja inválido ou
     * não esteja associado a um profissional de saúde, será necessário
     * verificar a propriedade "retorno" (payload) para identificar
     * que profissional não foi encontrado.
     */
    cpf(numero) {
        const options = {
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
            path: "/api/fhir/r4/Organization/" + cnpj,
        };

        return this.makeRequest(options);
    }

    /**
     * Requisita informações sobre os papéis desempenhados por um profissional de saúde num
     * dado estabelecimento.
     *
     * @param {string} cns CNS do profissional de saúde
     * @param {string} cnes Código CNES do estabelecimento de saúde
     * @returns {Promise<Resposta>}
     */
    lotacoes(cns, cnes) {
        const practitioner =
            "practitioner.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" +
            cns;
        const organization =
            "organization.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" +
            cnes;

        const options = {
            path:
                "/api/fhir/r4/PractitionerRole?" + practitioner + "&" + organization,
        };

        return this.makeRequest(options);
    }

    /**
     * Requisita informações sobre os papéis desempenhados por um profissional de saúde.
     *
     * @param {string} cns CNS do profissional de saúde
     * @returns {Promise<Resposta>}
     */
    lotacaoPorCns(cns) {
        const practitioner =
            "practitioner.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" +
            cns;

        const options = {
            path:
                "/api/fhir/r4/PractitionerRole?" + practitioner,
        };

        return this.makeRequest(options);
    }

    /**
     * Requisita informações sobre os papéis desempenhados por
     * profissionais lotados num estabelecimento de saúde.
     *
     * @param {string} cnes Código CNES do estabelecimento de saúde
     * @returns {Promise<Resposta>}
     */
    lotacaoPorCnes(cnes) {

        const organization =
            "organization.identifier=http%3A%2F%2Frnds.saude.gov.br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" +
            cnes;

        const options = {
            path:
                "/api/fhir/r4/PractitionerRole?" + organization,
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
                return {...resposta, retorno: ""};
            }

            const ids = json.entry[0].resource.identifier;
            const idx = ids.findIndex((i) => cnsOficial(i));
            return {...resposta, retorno: ids[idx].value};
        };

        return this.paciente(numero).then(extraiCnsSeEncontrado);
    }

    /**
     * Retorna promise com a indicação de sucesso ou falha
     * na obtenção de token de acesso.
     */
    verifica() {
        return this.cache.getToken().then(t => t.length !== 0).catch(() => false);
    }
}
