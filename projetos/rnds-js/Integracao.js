const fs = require("fs");
const https = require("follow-redirects").https;

// TODO mensagem quando codigo de retorno é 500

// TODO criar classe
// 1. construtor verifica todas as vars
// 2. cache 'certificate' file (pfx)
// 3. instancia falha se não for possível obter token.

class RNDS {
  constructor() {}
}

/**
 * Variáveis de ambiente empregadas pelas funções.
 */
const auth = process.env.RNDS_AUTH;
const ehr = process.env.RNDS_EHR;
const certificado = process.env.RNDS_CERTIFICADO_ENDERECO;
const senha = process.env.RNDS_CERTIFICADO_SENHA;
const requisitante = process.env.RNDS_REQUISITANTE_CNS;

module.exports = RNDS;

new RNDS();
