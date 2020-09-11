package com.github.kyriosdata.rnds;



import java.io.File;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/*
Depdendências:
   - org.apache.httpcomponents:httpclient:4.5.10
   - org.apache.commons:commons-lang3:3.9
   - commons-beanutils:commons-beanutils:1.9.4
*/
public class MutalTLSMain {
    private static final String KEY_STORE_PATH = "f:/tmp" +
            "/certificados/certificado.jks";
    private static final String KEY_STORE_PASSWORD = "secret";
    private static final String PRIVATE_KEY_PASSWORD = "secret";

    private static final boolean DEBUG_SSL = false;

    public static void main(String[] args) throws Exception {
        if (DEBUG_SSL) {
            System.setProperty("javax.net.debug", "all");
        }

        SSLContext sslcontext;
        SSLConnectionSocketFactory sslSocketFactory;
        HttpGet get;
        CloseableHttpClient httpClient;
        CloseableHttpResponse response;

        // o alias do certificado (key pair) no arquivo JKS deve ser "client"
        sslcontext = SSLContexts.custom()
                .loadKeyMaterial(
                        new File(KEY_STORE_PATH), KEY_STORE_PASSWORD.toCharArray(),
                        PRIVATE_KEY_PASSWORD.toCharArray() )
                .loadTrustMaterial(new File(KEY_STORE_PATH), KEY_STORE_PASSWORD.toCharArray())
                .build();

        sslSocketFactory = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1.2" },
                null,
                new NoopHostnameVerifier());

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        String endpoint;

        // producao
        endpoint = "https://ehr-auth.saude.gov.br/api/token";

        get = new HttpGet(endpoint);

        get.addHeader("accept", "application/json");

        response = httpClient.execute(get);

        System.out.println();

        System.out.println(response.getStatusLine());
        System.out.println( EntityUtils.toString(response.getEntity()) );

        System.out.println();

        if (response.getAllHeaders() != null) {
            for (Header item : response.getAllHeaders()) {
                System.out.println(item.getName() + ": " + item.getValue());
            }
        }
    }
}

