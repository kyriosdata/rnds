/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds.application;

import com.github.kyriosdata.rnds.RNDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
public class CNES {

    static final Logger logger =
            LoggerFactory.getLogger(CNES.class);
    /**
     * Endereço do qual o token será obtido (ambiente de homologação).
     */
    public static final String HOMOLOGACAO =
            "https://ehr-services-hmg.saude.gov.br/api/fhir/r4/";

    /**
     * Endereço do qual o token será obtido (ambiente de produção).
     */
    public static final String PRODUCAO =
            "https://ehr-auth.saude.gov.br/api/token";

    /**
     * Aplicação que obtém informações sobre o estabelecimento de saúde
     * para o CNES fornecido.
     *
     * @param args O primeiro argumento é o CNES do estabelecimento e o
     *             segundo deve ser o CPF do responsável pela requisição.
     */
    public static void main(String[] args) throws IOException {

        final String arquivo = System.getenv("RNDS_CERT_FILE");
        logger.info("RNDS_CERT_FILE: {}", arquivo);

        final String auth = System.getenv("RNDS_AUTH");
        logger.info("RNDS_AUTH: {}", auth);

        final String srv = System.getenv("RNDS_SRV");
        logger.info("RNDS_SRV: {}", srv);

        String senha = System.getenv("RNDS_CERT_SENHA");
        final char[] password = senha != null ? senha.toCharArray() : null;
        logger.info("RNDS_CERT_SENHA: ******");

        if (arquivo == null || auth == null || password == null) {
            logger.info("Pelo menos uma var de ambiente não definida");
            return;
        }

        final String token = RNDS.getToken(auth, arquivo, password);
        if (token == null) {
            logger.warn("FALHA AO OBTER TOKEN");
            return;
        }

        final String payload = RNDS.cnes(srv, token, args[0], args[1]);

        logger.info("PAYLOAD: {}", payload);
    }
}
