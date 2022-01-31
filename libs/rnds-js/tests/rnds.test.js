const { RNDS } = require("../rnds");

test("busca por cnpj", async (done) => {
  const rnds = await RNDS.cliente(false, true, false);
  mostre(await rnds.cache.getToken());
  const pessoaJuridica = await rnds.cnpj("01567601000143");
  const dados = JSON.parse(pessoaJuridica.retorno);
  expect(dados.name).toBe("UNIVERSIDADE FEDERAL DE GOIAS");
  done();
});

test("capability statement and token check", async (done) => {
  const cliente = await RNDS.cliente(false, true, false);
  expect(cliente).toBeDefined();
  done();
});
