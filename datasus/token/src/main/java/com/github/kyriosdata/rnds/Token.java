package com.github.kyriosdata.rnds;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.Objects;

public class Token {
    private static final Logger logger =
            LoggerFactory.getLogger(Token.class);

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
        Class<Token> appClass = Token.class;
        return appClass.getClassLoader().getResource(arquivo).getPath();
    }

    /**
     * Aplicação que recupera <i>token</i> de acesso aos serviços da RNDS.
     * @param args Nenhum argumento é esperado via linha de comandos.
     */
    public static void main(String[] args) {
        final String arquivo = System.getenv("RNDS_CERT_FILE");
        Objects.requireNonNull(arquivo, "RNDS_CERT_FILE não definida");

        final String server = System.getenv("RNDS_AUTH_SERVER");
        Objects.requireNonNull(server, "RNDS_AUTH_SERVER não definida");

        String senha = System.getenv("RNDS_CERT_SENHA");
        Objects.requireNonNull(senha, "RNDS_CERT_SENHA não definida");
        final char[] password = senha.toCharArray();

        final String token = getToken(SERVER, arquivo, password);
        System.out.println(token);
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
     * O valor {@code null} é retornado em caso de falha.
     */
    private static String getToken(
            final String server,
            final String file,
            final char[] keyStorePassord) {
        try {
            SSLContext context = sslContext(file, keyStorePassord);
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

        return HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();
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
