package com.github.kyriosdata.rnds;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;

public class TokenApplication {
    private static final String KEY_STORE_PASSWORD = "secret";
    private static final String KEY_STORE_PATH = "certificado.jks";

    private static final boolean DEBUG_SSL = false;
    private static final String EHR_AUTH =
            "https://ehr-auth.saude.gov.br/api/token";

    /**
     * Obtém path completo do nome do arquivo fornecido que se encontra
     * no diretório resources.
     *
     * @param arquivo Nome do arquivo contido no diretório "resources".
     *
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
        
        String file = fromResource(KEY_STORE_PATH);
        char[] keyStorePassord = KEY_STORE_PASSWORD.toCharArray();

        SSLContext sslcontext = sslContext(file, keyStorePassord);

        SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(
                        sslcontext,
                        new String[]{"TLSv1.2"},
                        null,
                        new NoopHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        // TODO definir em tempo de execução
        final String endpoint = EHR_AUTH;

        HttpGet get = new HttpGet(endpoint);

        get.addHeader("accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(get);

        System.out.println();

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));

        System.out.println();

        final Header[] allHeaders = response.getAllHeaders();
        if (allHeaders == null) {
            return;
        }

        Arrays.stream(allHeaders).forEach(TokenApplication::showHeader);
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

        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
                keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(),
                new SecureRandom());

        return sslContext;
    }
}
