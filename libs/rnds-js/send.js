//const { https } = require("follow-redirects");

//import followRedirects from "follow-redirects";
//const { https } = followRedirects;

// import em vez de require e https em vez de follow-redirects.
import https from "https";

/**
 * Cria função para requisição HTTP(s).
 * @param {function} logging Serviço para registro de log, por exemplo, console.log.
 * @returns {function(*, *): Promise<unknown>} com retorno de requisição http(s).
 */
export function sendService(logging) {
  /**
   * Envia requisição https conforme opções e, se for o caso,
   * com o payload indicado. É esperado que o retorno satisfatório
   * coincida com o código fornecido. Em caso de sucesso, a
   * callback é chamada com a resposta retornada (JavaScript object
   * correspondente ao JSON retornado) e, caso o código
   * esperado não seja o retornado, então instância de erro é retornada.
   *
   * @param {object} options Conjunto de propriedades que estabelecem a
   * configuração para a requisição.
   *
   * @param {function} callback Função a ser chamada com três argumentos, na
   * seguinte ordem: (a) código de retorno; (b) o conteúdo retornado e
   * (c) headers retornados.
   *
   * @param {string} payload Conteúdo a ser submetido. Se não fornecido,
   * então nada será enviado.
   *
   * @returns {Promise<Resposta>} Promise para a resposta obtida pela
   * execução da requisição.
   */
  return function send(options, payload) {
    logging(options.method, options.path);

    return new Promise((resolve, reject) => {
      const req = https.request(options, function (res) {
        const chunks = [];

        res.on("data", (chunk) => chunks.push(chunk));

        res.on("end", function (chunk) {
          const buffer = Buffer.concat(chunks);
          const texto = buffer.length === 0 ? "" : buffer.toString();

          // Repassado o código de retorno, o retorno e headers
          // (em vários cenários os headers não são relevantes)
          logging(options.path, res.statusCode);
          resolve({
            code: res.statusCode,
            retorno: texto,
            headers: res.headers,
          });
        });

        res.on("error", function (error) {
          reject({
            msg: "Ocorreu um erro (requisição não executada)",
            erro: error,
          });
        });
      });

      // Se não fornecido ou vazio, não será enviado.
      if (payload) {
        req.write(payload);
      }

      req.end();
    });
  };
}

