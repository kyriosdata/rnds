/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Classe exclusiva para obtenção de <i>token</i> de acesso aos serviços
 * oferecidos pela RNDS.
 */
public class AccessToken {
    private static SSLContext sslCtx(final String keystore,
                                     final char[] password)
            throws GeneralSecurityException {
        KeyStore keyStore = getKeyStore(keystore, password);

        String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(defaultAlgorithm);
        keyManagerFactory.init(keyStore, password);

        String algorithmTrust = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(algorithmTrust);
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());

        // É necessário?
        // SSLContext.setDefault(sslContext);

        return sslContext;
    }

    /**
     * Obtém {@link KeyStore} a partir de caminho para arquivo contendo o
     * certificado digital.
     *
     * @param keystoreEndereco Endereço para arquivo contendo o certificado
     *                         digital ou endereço <i>web</i>, por exemplo,
     *                         iniciado por "http".
     * @param password         Senha de acesso ao conteúdo do certificado
     *                         digital.
     * @return Instância de {@link KeyStore} devidamente carregada com o
     * certificado digital fornecido.
     */
    private static KeyStore getKeyStore(
            final String keystoreEndereco, char[] password) {

        try (InputStream is = fromEndereco(keystoreEndereco)) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, password);
            return keystore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException
                | CertificateException exception) {
            RNDS.logger.warning(exception.toString());
            RNDS.logger.warning("não foi possível obter keystore");
            return null;
        }
    }

    /**
     * Extrai do JSON fornecido o valor do par cujo nome é "access_token".
     * Assume que a entrada contém JSON válido e que o par de nome
     * "access_token" está presente.
     *
     * @param payload O JSON do qual o valor será extraído.
     * @return O valor do par cujo nome é "access_token" no JSON fornecido.
     */
    private static String extrairToken(final String payload) {
        final int indice = payload.indexOf("access_token");
        final int separador = payload.indexOf(":", indice);
        final int inicioToken = payload.indexOf("\"", separador) + 1;
        final int fimToken = payload.indexOf("\"", inicioToken);
        return payload.substring(inicioToken, fimToken);
    }

    private static CloseableHttpClient getClient(final SSLContext sslcontext) {
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
     * Obtém {@link InputStream} para endereço fornecido. O endereço pode ser
     * web, ou seja, iniciado por <i>http</i>, ou um arquivo local, quando
     * não é iniciado por <i>http</i>.
     *
     * @param endereco Endereço para o qual {@link InputStream} será obtido.
     * @return {@link InputStream} para o endereço fornecido.
     * @throws RuntimeException Em caso de falha na tentativa de obter
     *                          {@link InputStream} para o endereço fornecido.
     */
    private static InputStream fromEndereco(final String endereco)
            throws IOException {
        if (endereco.toLowerCase().startsWith("http")) {
            return new URL(endereco).openStream();
        } else {
            return new FileInputStream(endereco);
        }
    }

    /**
     * Obtém <i>token</i> para acesso aos serviços de integração da RNDS.
     *
     * @param server           Endereço do serviço que verifica o certificado
     *                         e, se devidamente autorizado, oferece o
     *                         <i>token</i> correspondente.
     * @param keystoreEndereco O endereço do arquivo contendo o certificado.
     *                         Pode se o caminho (<i>path</i>) ou endereço
     *                         <i>web</i>, iniciado por <i>http</i>.
     * @param keyStorePassword A senha de acesso ao certificado.
     * @return O <i>token</i> a ser utilizado para requisitar serviços da RNDS.
     * O valor {@code null} é retornado em caso de falha.
     */
    public static String get(
            final String server,
            final String keystoreEndereco,
            final char[] keyStorePassword) {
        try {
            SSLContext context = sslCtx(keystoreEndereco, keyStorePassword);
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
            RNDS.logger.warning(e.toString());
        }

        return null;
    }
}