const { RNDS } = require("../rnds");

test("busca por cns", async () => {
  const rnds = new RNDS(false, true);
  const pessoaJuridica = await rnds.cnpj("01567601000143");
  const dados = JSON.parse(pessoaJuridica.retorno);
  expect(dados.name).toBe("UNIVERSIDADE FEDERAL DE GOIAS");
});

test("capability statement and token check", async (done) => {
  const cliente = await RNDS.cliente(false, true, true);
  expect(cliente).toBeDefined();
  done();
});
