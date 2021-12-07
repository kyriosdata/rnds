import chalk from "chalk";

/**
 * Estrutura que mantém os valores empregados para configuração
 * do acesso aos serviços oferecidos pela RNDS.
 *
 * @typedef {Object} Configuracao
 * @property {Object} auth - O endereço do serviço de autenticação
 * @property {Object} certificado - O endereço (path) onde se encontra o
 * certificado digital.
 * @property {Object} senha - A senha para acesso ao certificado digital.
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
    return {
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
            console.log(objeto.nome, chalk.blue("OK"), objeto.valor);
        } else {
            console.log(objeto.nome, chalk.red("variável não definida"));
        }
    }
}
