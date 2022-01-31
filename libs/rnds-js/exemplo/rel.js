import RNDS from "rnds";

async function applicacao() {
  const rnds = new RNDS(true, true);

  const resultado = await rnds.notificar("01567601000143");
  const dados = JSON.parse(resultado.retorno.toString());
  mostre(dados);
}

await applicacao();
