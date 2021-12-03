import chalk from "chalk";

import fs from "fs";
const pacote = JSON.parse(fs.readFileSync('./package.json'));

console.log(chalk.bold("rnds-js"), pacote.version);

/**
 * Variáveis exigidas para configuração do acesso
 * aos serviços oferecidos pela RNDS.
 */
const VARIAVEIS = [
    {
        nome: "RNDS_AUTH",
        valor: process.env.RNDS_AUTH
    },
    {
        nome: "RNDS_CERTIFICADO_ENDERECO",
        valor: process.env.RNDS_CERTIFICADO_ENDERECO
    },
    {
        nome: "RNDS_CERTIFICADO_SENHA",
        valor: process.env.RNDS_CERTIFICADO_SENHA
    },
    {
        nome: "RNDS_EHR",
        valor: process.env.RNDS_EHR
    },
    {
        nome: "RNDS_REQUISITANTE_CNS",
        valor: process.env.RNDS_REQUISITANTE_CNS
    },
    {
        nome: "RNDS_REQUISITANTE_UF",
        valor: process.env.RNDS_REQUISITANTE_UF
    },
];

function check(valor) {
    return valor && valor.length !== 0;
}

/**
 * Exibe cada um dos valores recuperados de variáveis
 * de ambiente utilizados na configuração do acesso aos
 * serviços oferecidos pela RNDS. Se a variável não
 * está definida, indicação de erro é exibida.
 */
function checkConfiguracao() {
    for (let variavel of VARIAVEIS) {
        if (check(variavel.valor)) {
            console.log(variavel.nome, chalk.blue("OK"), variavel.valor);
        } else {
            console.log(variavel.nome, chaclk.red("variável não definida"));
        }
    }
}

checkConfiguracao();
