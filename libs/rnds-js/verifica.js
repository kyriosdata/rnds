import chalk from "chalk";
import { verificacao, getVersao } from "./configuracao.js";

mostre(chalk.bold("rnds-js"), getVersao());

verificacao();

