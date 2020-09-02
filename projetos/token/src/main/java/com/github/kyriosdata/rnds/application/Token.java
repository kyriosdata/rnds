/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds.application;

import com.github.kyriosdata.rnds.RNDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aplicação que ilustra uso da biblioteca RNDS-LIB
 * para recuperação de <i>token</i> de acesso aos serviços
 * da RNDS.
 *
 * <p>Entrada para esta aplicação é fornecida por meio de variáveis
 * de ambiente:</p>
 * <ul>
 *     <li><b>RNDS_CERT_FILE</b>. Caminho completo do arquivo
 *     contendo o certificado a ser empregado para a obtenção do
 *     <i>token</i>.</li>
 *     <li><b>RNDS_CERT_SENHA</b>. A senha para acesso ao conteúdo
 *     do certificado.</li>
 *     <li><b>RNDS_AUTH_SERVER</b>. O serviço oferecido pela RNDS
 *     pelo qual o <i>token</i> será obtido.</li>
 * </ul>
 */
public class Token {

    /**
     * Endereço do qual o token será obtido (ambiente de homologação).
     */
    public static final String HOMOLOGACAO =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    /**
     * Endereço do qual o token será obtido (ambiente de produção).
     */
    public static final String PRODUCAO =
            "https://ehr-auth.saude.gov.br/api/token";

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
        final char[] password = senha != null ? senha.toCharArray() : null;
        logger.info("RNDS_CERT_SENHA: ******");

        if (arquivo == null || server == null || password == null) {
            logger.info("Pelo menos uma var de ambiente não definida");
            return;
        }

        final String token = RNDS.getToken(server, arquivo, password);
        if (token == null) {
            logger.warn("FALHA AO OBTER TOKEN");
        } else {
            logger.info("TOKEN: {}...", token.substring(0, 19));
            logger.info("TOKEN recuperado corretamente.");
        }
    }
}
