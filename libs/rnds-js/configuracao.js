import chalk from "chalk";
import fs from "fs";
import Token from "./Token.js";
import {sendService} from "./send.js";

/**
 * Estrutura que mantém os valores empregados para configuração
 * e acesso aos serviços oferecidos pela RNDS.
 *
 * @typedef {Object} Configuracao
 * @property {Object} auth - O endereço do serviço de autenticação
 * @property {Object} certificado - O endereço (path) onde se encontra o
 * certificado digital.
 * @property {Object} senha - A senha para acesso ao certificado digital.
 * @property {Buffer} pfx - Conteúdo do certificado.
 * @property {Object} ehr - O endereço do serviço de registros de saúde.
 * @property {Object} requisitante - O CNS do requisitante em nome do qual
 * requisições são submetidas.
 */

/**
 * Obtém valores de variáveis de ambiente empregadas
 * para configurar acesso aos serviços da RNDS.
 *
 * As variáveis são: (a) RNDS_AUTH; (b) RNDS_CERTIFICADO_ENDERECO;
 * (c) RNDS_CERTIFICADO_SENHA; (d) RNDS_EHR, (e) RNDS_REQUISITANTE_CNS
 * e (f) RNDS_REQUISITANTE_UF.
 *
 * @returns {Configuracao} a configuração para acesso aos serviços
 * oferecidos pela RNDS.
 */
export function obtemConfiguracao() {
    const cfg = {
        auth: {
            nome: "RNDS_AUTH",
            valor: process.env.RNDS_AUTH
        },
        certificado: {
            nome: "RNDS_CERTIFICADO_ENDERECO",
            valor: process.env.RNDS_CERTIFICADO_ENDERECO
        },
        senha: {
            nome: "RNDS_CERTIFICADO_SENHA",
            valor: process.env.RNDS_CERTIFICADO_SENHA
        },
        pfx: {
            nome: "PFX",
            valor: undefined
        },
        ehr: {
            nome: "RNDS_EHR",
            valor: process.env.RNDS_EHR
        },
        requisitante: {
            nome: "RNDS_REQUISITANTE_CNS",
            valor: process.env.RNDS_REQUISITANTE_CNS
        },
        uf: {
            nome: "RNDS_REQUISITANTE_UF",
            valor: process.env.RNDS_REQUISITANTE_UF
        }
    };

    try {
        cfg.pfx.valor = fs.readFileSync(cfg.certificado.valor);
    } catch (error) {
        throw new Error(
            `erro ao carregar arquivo pfx: ${cfg.certificado.valor}`
        );
    }

    return cfg;
}

/**
 * Exibe a configuração fornecida com a indicação da
 * presença ou não de valor para cada uma das
 * variáveis.
 */
export function exibeConfiguracao(configuracao) {
    function check(valor) {
        return valor && valor.length !== 0;
    }

    for (let key of Object.keys(configuracao)) {
        const objeto = configuracao[key];
        if (check(objeto.valor)) {
            mostre(objeto.nome, chalk.blue("OK"), objeto.valor);
        } else {
            mostre(objeto.nome, chalk.red("variável não definida"));
        }
    }
}

/**
 * Recupera a versão da biblioteca rnds-js.
 *
 * @returns {string} Versão da biblioteca (semantic versioning).
 */
export function getVersao() {
    const json = fs.readFileSync("./package.json").toString();
    return JSON.parse(json).version;
}

/**
 * Realiza tentativa de obtenção de token conforme a configuração
 * disponível e exibe na saída padrão o resultado correspondente.
 */
export function verificacao() {
    const configuracao = obtemConfiguracao();

    const falha = chalk.red.inverse;
    const ok = chalk.green.inverse;

    function exibeToken(t) {
        mostre("Token", chalk.blue("OK"), t.length, "bytes");
    }

    function getOnrejected(erro) {
        if (erro.erro.code === "ECONNREFUSED") {
            mostre("Verifique os endereços de acesso à RNDS.");
        }

        mostre(falha("Não foi possível conexão com a RNDS."));
    }

    const token = new Token(configuracao, true, sendService);
    token.getToken()
        .then(exibeToken)
        .then(() => mostre(ok("Estabelecida conexão com a RNDS.")))
        .catch(getOnrejected);
}

