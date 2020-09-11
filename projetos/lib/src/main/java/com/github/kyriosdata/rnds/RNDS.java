/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * Classe que implementa funções utilitárias para acesso aos serviços da RNDS.
 */
public class RNDS {

    static final Logger logger =
            LoggerFactory.getLogger(RNDS.class);

    private static SSLContext sslContext(final String keystoreFile,
                                         final char[] password)
            throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream in = new FileInputStream(keystoreFile)) {
            keystore.load(in, password);
        }

        String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(defaultAlgorithm);
        keyManagerFactory.init(keystore, password);

        String algorithmTrust = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(algorithmTrust);
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());

        return sslContext;
    }

    /**
     * Extrai do JSON fornecido o valor do par cujo nome é "access_token".
     * Assume que a entrada contém JSON válido e que o par de nome
     * "access_token" está presente.
     *
     * @param payload O JSON do qual o valor será extraído.
     * @return O valor do par cujo nome é "access_token" no JSON fornecido.
     */
    static String extrairToken(final String payload) {
        final int indice = payload.indexOf("access_token");
        final int separador = payload.indexOf(":", indice);
        final int inicioToken = payload.indexOf("\"", separador) + 1;
        final int fimToken = payload.indexOf("\"", inicioToken);
        return payload.substring(inicioToken, fimToken);
    }

    static CloseableHttpClient getClient(final SSLContext sslcontext) {
        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[]{"TLSv1.2"},
                        null,
                        new NoopHostnameVerifier());

        return HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();
    }

    /**
     * Obtém <i>token</i> para acesso aos serviços de integração da RNDS.
     *
     * @param server           Endereço do serviço que verifica o certificado
     *                         e, se devidamente autorizado, oferece o
     *                         <i>token</i> correspondente.
     * @param file             O arquivo (path completo) contendo o certificado.
     * @param keyStorePassword A senha de acesso ao certificado.
     * @return O <i>token</i> a ser utilizado para requisitar serviços da RNDS.
     * O valor {@code null} é retornado em caso de falha.
     */
    public static String getToken(
            final String server,
            final String file,
            final char[] keyStorePassword) {
        try {
            SSLContext context = sslContext(file, keyStorePassword);
            try (CloseableHttpClient cliente = getClient(context)) {
                HttpGet get = new HttpGet(server);
                get.addHeader("accept", "application/json");
                try (CloseableHttpResponse response = cliente.execute(get)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    response.getEntity().writeTo(baos);

                    // Resposta do servidor
                    String payload = baos.toString();

                    return extrairToken(payload);
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            logger.warn(e.toString());
        }

        return null;
    }
    
    /**
     * Obtém informações sobre estabelecimento de saúde cujo CNES é fornecido.
     *
     * @param srv   O serviço que fornecerá a resposta.
     * @param token O <i>token</i> de acesso ao serviço.
     * @param cnes  O código CNES do estabelecimento.
     * @param cpf   O CPF do responsável pela requisição. Deve estar associado
     *              ao <i>token</i>.
     * @return O JSON retornado pelo serviço ou o valor {@code null} em caso
     * de exceção.
     */
    public static String cnes(String srv, String token, String cnes,
                              String cpf) {
        try {
            final String CNES_REQUEST = "fhir/r4/Organization/" + cnes;
            logger.info("SERVICO: {}", CNES_REQUEST);

            final URL url = new URL(srv + CNES_REQUEST);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", cpf);

            final int codigo = servico.getResponseCode();
            logger.warn("RESPONSE CODE: {}", codigo);

            if (codigo != 200) {
                logger.warn(fromInputStream(servico.getErrorStream()));
                return null;
            }

            return fromInputStream(servico.getInputStream());
        } catch (IOException exception) {
            logger.warn("EXCECAO: {}", exception);
            return null;
        }
    }

    public static String profissional(String srv, String token, String cns,
                                      String cpf) {
        try {
            final String PROFISSIONAL = "fhir/r4/Practitioner/" + cns;
            logger.info("SERVICO: {}", PROFISSIONAL);

            final URL url = new URL(srv + PROFISSIONAL);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
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

    public static String cpf(String srv, String token, String cpf,
                             String authorization) {
        try {
            final String query = "?identifier=http://rnds.saude.gov" +
                    ".br/fhir/r4/NamingSystem/cpf|" + cpf;
            final String CPF = "fhir/r4/Practitioner" + query;
            logger.info("SERVICO: {}", CPF);

            final URL url = new URL(srv + CPF);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", authorization);

            final int codigo = servico.getResponseCode();
            logger.warn("RESPONSE CODE: {}", codigo);

            if (codigo != 200) {
                logger.warn(fromInputStream(servico.getErrorStream()));
                return null;
            }

            return fromInputStream(servico.getInputStream());
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
