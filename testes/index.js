const { RNDS } = require("rnds");

async function inicio() {
  const cliente = await RNDS.cliente(true, true, true);

  // Apenas para assegurar que todas as funções são executadas,
  // pois esta já é chamada (veja parâmetros acima).
  cliente.checkVersion();

  // CNES
  const cnes = await cliente.cnes("2337991");
  let resposta = JSON.parse(cnes.retorno);
  console.log("cnes", resposta.nome === "LABORATORIO ROMULO ROCHA");

  // CNES
  const cns = await cliente.cns("980016287385192");
  resposta = JSON.parse(cns.retorno);
  const oficial = resposta.name.find((e) => e.use === "official").text;
  console.log("cns", oficial === "THALYTA RENATA ARAUJO SANTOS");
}

inicio();
