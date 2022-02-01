import RNDS from "./rnds.js";

async function applicacao() {
    const rnds = new RNDS(true, true);

    const pessoaJuridica = await rnds.cnpj("01567601000143");
    const dados = JSON.parse(pessoaJuridica.retorno.toString());
    console.log(dados);
}

await applicacao();