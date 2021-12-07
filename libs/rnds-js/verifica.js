import chalk from "chalk";
import Token from "./Token.js";
import fs from "fs";
import {sendService} from "./send.js";
import {obtemConfiguracao, exibeConfiguracao} from "./configuracao.js";

console.log(chalk.bold("rnds-js"), getVersao());

function getVersao() {
    const json = fs.readFileSync("./package.json").toString();
    return JSON.parse(json).version;
}

function check(nome, valor) {
    if (!valor || valor.length === 0) {
        throw new Error(`variável ${nome} não definida ou vazia`);
    }

    return valor;
}

const configuracao = obtemConfiguracao();
exibeConfiguracao(configuracao);

const token = new Token(console.log, configuracao, true, sendService(console.log));
token.getToken().then(console.log).catch((erro) => console.log(erro));
