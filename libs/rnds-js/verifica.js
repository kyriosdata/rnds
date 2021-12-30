import chalk from "chalk";
import Token from "./Token.js";
import {sendService} from "./send.js";
import {obtemConfiguracao, exibeConfiguracao, getVersao} from "./configuracao.js";

console.log(chalk.bold("rnds-js"), getVersao());

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