import chalk from "chalk";
import {verificacao, getVersao} from "./configuracao.js";

console.log(chalk.bold("rnds-js"), getVersao());

verificacao();