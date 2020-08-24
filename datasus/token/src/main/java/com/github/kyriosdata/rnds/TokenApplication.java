package com.github.kyriosdata.rnds;

import org.apache.http.Header;
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
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

public class TokenApplication {
    private static final String KEY_STORE_PASSWORD = "secret";
    private static final String KEY_STORE_PATH = "certificado.jks";

    private static final boolean DEBUG_SSL = false;

    /**
     * Endereço do qual o token será obtido.
     */
    private static final String SERVER =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    /**
     * Obtém path completo do nome do arquivo fornecido que se encontra
     * no diretório resources.
     *
     * @param arquivo Nome do arquivo contido no diretório "resources".
     * @return O caminho completo para o arquivo cujo nome é fornecido.
     */
    private static String fromResource(final String arquivo) {
        Class<TokenApplication> appClass = TokenApplication.class;
        return appClass.getClassLoader().getResource(arquivo).getPath();
    }

    public static void main(String[] args) throws Exception {
        if (DEBUG_SSL) {
            System.setProperty("javax.net.debug", "all");
        }

        final String token = getTokenFromRnds(SERVER,
                fromResource(KEY_STORE_PATH), KEY_STORE_PASSWORD.toCharArray());
        System.out.println(token);

//        final Header[] allHeaders = response.getAllHeaders();
//        if (allHeaders == null) {
//            return;
//        }
//
//        System.out.println();
//        System.out.println(KeyStore.getDefaultType());
//        System.out.println(statusLine);
//        System.out.println(payload);
//        System.out.println();
//
//        Arrays.stream(allHeaders).forEach(TokenApplication::showHeader);
    }

    /**
     * Obtém <i>token</i> para acesso ao portal de serviços da RNDS.
     *
     * @param server          Endereço do serviço que verifica o certificado
     *                        e, se devidamente autorizado, oferece o
     *                        <i>token</i> correspondente.
     * @param file            O arquivo (path completo) contendo o certificado.
     * @param keyStorePassord A senha de acesso ao certificado.
     * @return O <i>token</i> a ser utilizado para requisitar serviços da RNDS.
     */
    private static String getTokenFromRnds(String server, String file,
                                           char[] keyStorePassord) {
        try {
            SSLContext sslcontext = sslContext(file, keyStorePassord);
            try (CloseableHttpClient httpClient = getClient(sslcontext)) {
                HttpGet get = new HttpGet(server);
                get.addHeader("accept", "application/json");
                try (CloseableHttpResponse response = httpClient.execute(get)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    response.getEntity().writeTo(baos);

                    // Resposta do servidor
                    String payload = baos.toString();

                    return extrairToken(payload);
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Extrai do JSON fornecido o valor do par cujo nome é "access_token".
     * Assume que a entrada contém JSON válido e que o par de nome
     * "access_token" está presente.
     *
     * @param payload O JSON do qual o valor será extraído.
     * @return O valor do par cujo nome é "access_token" no JSON fornecido.
     */
    private static String extrairToken(String payload) {
        final int indice = payload.indexOf("access_token");
        final int separador = payload.indexOf(":", indice);
        final int inicioToken = payload.indexOf("\"", separador) + 1;
        final int fimToken = payload.indexOf("\"", inicioToken);
        return payload.substring(inicioToken, fimToken);
    }

    private static CloseableHttpClient getClient(SSLContext sslcontext) {

        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[]{"TLSv1.2"},
                        null,
                        new NoopHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        return httpClient;
    }

    private static void showHeader(Header item) {
        String h = String.join(" : ", item.getName(), item.getValue());
        System.out.println(h);
    }

    private static SSLContext sslContext(String keystoreFile, char[] password)
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
}
