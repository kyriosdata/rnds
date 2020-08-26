/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds.application;

import com.github.kyriosdata.rnds.RNDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Token {

    /**
     * Endereço do qual o token será obtido.
     */
    private static final String SERVER =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    /**
     * Aplicação que recupera <i>token</i> de acesso aos serviços da RNDS.
     * @param args Nenhum argumento é esperado via linha de comandos.
     */
    public static void main(String[] args) {
        final Logger logger =
                LoggerFactory.getLogger(Token.class);

        final String arquivo = System.getenv("RNDS_CERT_FILE");
        logger.info("RNDS_CERT_FILE: {}", arquivo);

        final String server = System.getenv("RNDS_AUTH_SERVER");
        logger.info("RNDS_AUTH_SERVER: {}", server);

        String senha = System.getenv("RNDS_CERT_SENHA");
        logger.info("RNDS_CERT_SENHA: ******");
        final char[] password = senha.toCharArray();

        if (arquivo == null || server == null || senha == null) {
            return;
        }

        final String token = RNDS.getToken(server, arquivo, password);
        if (token == null) {
            logger.warn("FALHA AO OBTER TOKEN");
        } else {
            logger.info("TOKEN: {}...", token.substring(0, 19));
        }
    }
}
