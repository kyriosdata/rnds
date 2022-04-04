import {obtemConfiguracao} from "../rnds";

import Token from "../Token";

import sendService from "../send";

const cfg = obtemConfiguracao();
const empty = () => {};
const send = sendService(empty);

test("variáveis de ambiente exigidas estão definidas", () => {
  expect(() => obtemConfiguracao()).not.toThrow(Error);
});

test("obter token (reutiliza cache)", async () => {
  const token = new Token(empty, cfg, true, send);
  expect(await token.getToken()).toBe(token.access_token);

  const tokenStore = token.access_token;
  expect(tokenStore).toBeTruthy();

  // Deve retornar mesmo token (cache)
  await token.getToken();

  expect(tokenStore).toBe(token.access_token);
  expect(token.access_token.length > 2000).toBeTruthy();
});

test("após limpar cache novo token é obtido", async (done) => {
  const cache = new Token(empty, cfg, true, send);

  const tokenA = await cache.getToken();
  expect(tokenA.length > 2000).toBe(true);

  // Clear cache
  cache.limpaToken();
  expect(cache.access_token).toBeFalsy();

  // Espere um pouco até a próxima requisição quando novo
  // token será retornado
  setTimeout(async () => {
    const tokenB = await cache.getToken();
    expect(tokenA).not.toBe(tokenB);
    done();
  }, 3000);
});
