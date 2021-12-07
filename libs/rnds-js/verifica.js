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

function exibeToken(t) {
    console.log("Token", chalk.blue("OK"), t.length, "bytes");
}

function applicacao() {
    const configuracao = obtemConfiguracao();
    exibeConfiguracao(configuracao);

    const token = new Token(configuracao, true, sendService);
    token.getToken().then(exibeToken).catch((erro) => console.log(erro));
}

applicacao();