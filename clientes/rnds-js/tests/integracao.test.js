const { RNDS, Token, obtemConfiguracao, sendService } = require("../rnds");

test("variáveis de ambiente exigidas estão definidas", () => {
  expect(() => obtemConfiguracao()).not.toThrow(Error);
});

test("obter token", async () => {
  const cfg = obtemConfiguracao();
  const send = sendService(console.log);
  const token = new Token(console.log, cfg, true, send);
  expect(await token.getToken()).toBe("access_token disponível");

  const tokenStore = token.access_token;
  expect(tokenStore).toBeTruthy();

  // Deve retornar mesmo token (cache)
  await token.getToken();

  expect(tokenStore).toBe(token.access_token);
});

test("busca por cns", async () => {
  // const rnds = new RNDS(false, true);
  // const pessoaJuridica = await rnds.cnpj("01567601000143");
  // const dados = JSON.parse(pessoaJuridica.retorno);
  // expect(dados.name).toBe("UNIVERSIDADE FEDERAL DE GOIAS");
});
