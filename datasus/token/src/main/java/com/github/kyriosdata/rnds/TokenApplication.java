package com.github.kyriosdata.rnds;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

public class TokenApplication {
    private static final String KEY_STORE_PATH = "certificado.jks";
    private static final String KEY_STORE_PASSWORD = "secret";
    private static final String PRIVATE_KEY_PASSWORD = "secret";

    private static final boolean DEBUG_SSL = false;
    private static final String EHR_AUTH =
            "https://ehr-auth.saude.gov.br/api/token";

    private static File fromResource(final String arquivo) {
        final URL url =
                TokenApplication.class.getClassLoader().getResource(arquivo);
        return new File(url.getPath());
    }

    public static void main(String[] args) throws Exception {
        if (DEBUG_SSL) {
            System.setProperty("javax.net.debug", "all");
        }

        // o alias do certificado (key pair) no arquivo JKS deve ser "client"
        File file = fromResource(KEY_STORE_PATH);
        char[] keyStorePassord = KEY_STORE_PASSWORD.toCharArray();
        char[] privateKeyPassword = PRIVATE_KEY_PASSWORD.toCharArray();

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(file, keyStorePassord, privateKeyPassword)
                .loadTrustMaterial(file, keyStorePassord)
                .build();

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
}