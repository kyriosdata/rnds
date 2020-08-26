/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds.application;

import com.github.kyriosdata.rnds.RNDS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
     * Aplicação que recupera <i>token</i> de acesso aos serviços da RNDS.
     *
     * @param args Nenhum argumento é esperado via linha de comandos.
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

        final String cnes = args[0];
        final String cpf = args[1];

        final String payload = cnes(srv, token, cnes, cpf);

        logger.info("PAYLOAD: {}", payload);
    }

    /**
     * Obtém informações sobre estabelecimento de saúde cujo CNES é fornecido.
     *
     * @param srv O serviço que fornecerá a resposta.
     * @param token O <i>token</i> de acesso ao serviço.
     * @param cnes O código CNES do estabelecimento.
     * @param cpf O CPF do responsável pela requisição. Deve estar associado
     *            ao <i>token</i>.
     * @return O JSON retornado pelo serviço ou o valor {@code null} em caso
     * de exceção.
     */
    private static String cnes(String srv, String token, String cnes,
                               String cpf) {
        try {
            final String CNES_REQUEST = "Organization/" + cnes;
            logger.info("SERVICO: {}", CNES_REQUEST);

            final URL url = new URL(srv + CNES_REQUEST);
            HttpsURLConnection servico = (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server", "Bearer " + token);
            servico.setRequestProperty("Authorization", cpf);

            final int codigo = servico.getResponseCode();
            logger.info("RESPONSE CODE: {}", codigo);
            final String payload = fromInputStream(servico.getInputStream());
            return payload;
        } catch (IOException exception) {
            logger.warn("EXCECAO: {}", exception);
            return null;
        }
    }

    /**
     * Método de conveniência para obter uma sequência de caracteres,
     * no formato UTF-8, a partir da entrada fornecida.
     *
     * @param is Entrada da qual o conteúdo será obtido.
     * @return Sequência de caracteres disponível na entrada ou valor
     * {@code null}, em caso de erro.
     */
    private static String fromInputStream(final InputStream is) {
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                return baos.toString("UTF-8");
            }
        } catch (IOException exception) {
            logger.warn("EXCECAO: {}", exception);
            return null;
        }
    }
}
