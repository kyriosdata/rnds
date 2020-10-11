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

import javax.net.ssl.HttpsURLConnection;
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
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Classe de conveniência para acesso aos serviços oferecidos pela RNDS,
 * tanto para o ambiente de homologação quanto ao ambiente de produção.
 */
public class RNDS {

    /**
     * Endereço do serviço de autenticação.
     */
    private String auth;

    /**
     * Endereço dos serviços de saúde.
     */
    private String ehr;

    /**
     * Caminho para o arquivo contendo o certificado digital.
     */
    private String keystore;

    /**
     * Senha de acesso ao conteúdo do certificado digital.
     */
    private char[] password;

    /**
     * Cache do requisitante (CNS) em nome do qual requisições serão
     * realizadas, se não dito o contrário por cada requisição.
     */
    private String requisitante;

    /**
     * Unidade da Federação na qual está localizado o estabelecimento de saúde.
     */
    private Estado estado;

    /**
     * <i>Token</i> de acesso aos serviços da RNDS. Este valor é reutilizado
     * por cerca de 30 minutos.
     */
    private String token;

    /**
     * Unidades da Federação do Brasil.
     */
    public enum Estado {
        AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA,
        PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO
    }

    /**
     * Cria objeto por meio do qual interação com a RNDS é encapsulada por
     * meio de simples operações.
     *
     * @param auth         Endereço do serviço de autenticação.
     * @param ehr          Endereço dos serviços de saúde.
     * @param keystore     Caminho para o arquivo contendo o certificado
     *                     digital.
     * @param password     Senha para acesso ao certificado digital.
     * @param requisitante CNS do profissional de saúde, lotado no
     *                     estabelecimento de saúde em questão (aquele para o
     *                     qual é fornecido o certificado digital) e em nome
     *                     do qual requisições serão feitas. Este
     *                     requisitante só será empregado para cada
     *                     requisição que não indicar especificamente em nome
     *                     de quem a requisição é realizada.
     * @param estado       Estado no qual está localizado o estabelecimento
     *                     de saúde.
     */
    public RNDS(final String auth,
                final String ehr,
                final String keystore,
                final char[] password,
                final String requisitante,
                final Estado estado) {
        // AUTH (prefixo https://) (sufixo /api/)
        Objects.requireNonNull(auth, "auth não definido");
        this.auth = String.format("https://%s/api/", auth);

        // EHR (prefixo https://) (sufixo /api/fhir/r4/)
        Objects.requireNonNull(ehr, "ehr não definido");
        this.ehr = ehr;
        this.keystore = Objects.requireNonNull(keystore, "keystore");
        this.password = Objects.requireNonNull(password, "password");
        this.requisitante = Objects.requireNonNull(requisitante,"requisitante");
        this.estado = Objects.requireNonNull(estado, "estado");

        // Cache token para uso na primeira requisição
        token();
    }

    static final Logger logger = Logger.getLogger("RNDS");

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
            logger.warning(exception.toString());
            logger.warning("não foi possível obter keystore");
            return null;
        }
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
     * @param keystoreEndereco O endereço do arquivo contendo o certificado.
     *                         Pode se o caminho (<i>path</i>) ou endereço
     *                         <i>web</i>, iniciado por <i>http</i>.
     * @param keyStorePassword A senha de acesso ao certificado.
     * @return O <i>token</i> a ser utilizado para requisitar serviços da RNDS.
     * O valor {@code null} é retornado em caso de falha.
     */
    public String getToken(
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
            logger.warning(e.toString());
        }

        return null;
    }

    public String token() {
        token = getToken(auth + "token", keystore, password);
        return token;
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
    public static String cnes(
            final String srv,
            final String token,
            final String cnes,
            final String cpf) {
        try {
            final String CNES_REQUEST = "Organization/" + cnes;
            logger.info("SERVICO: " + CNES_REQUEST);

            final URL url = new URL(srv + CNES_REQUEST);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", cpf);

            final int codigo = servico.getResponseCode();
            logger.warning("RESPONSE CODE: " + codigo);

            if (codigo != 200) {
                final String msg = fromInputStream(servico.getErrorStream());
                logger.warning(msg);
                return msg;
            }

            return fromInputStream(servico.getInputStream());
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }

    /**
     * Obtém a URI empregada para requisitar <i>token</i> de acesso.
     * @return A sequência completa da URI para requisição de <i>token</i>
     * de acesso.
     */
    public String forAuth() {
        return String.format("https://%s/api/token", auth);
    }

    public String forEhr(String path) {
        return String.format("https://%s/%s", ehr, path);
    }

    public String forAuth(String path, String query) {
        return String.format("https://%s/api/fhir/r4/%s/?%s", ehr, path, query);
    }

    public String cnpj(final String cnpj) {
        try {
            final String CNES_REQUEST = ehr + "Organization/" + cnpj;
            logger.info("SERVICO: " + CNES_REQUEST);

            final URL url = new URL(CNES_REQUEST);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", requisitante);

            final int codigo = servico.getResponseCode();

            if (codigo != 200) {
                final String msg = fromInputStream(servico.getErrorStream());
                logger.warning(msg);
                return msg;
            }

            return fromInputStream(servico.getInputStream());
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }

    public String cnes(final String cnes) {
        return cnes(ehr, token, cnes, requisitante);
    }

    public String profissional(
            final String srv,
            final String token,
            final String cns,
            final String requisitanteCns) {
        try {
            final String PROFISSIONAL = "Practitioner/" + cns;
            logger.info("SERVICO: " + PROFISSIONAL);

            final URL url = new URL(srv + PROFISSIONAL);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", requisitanteCns);

            final int codigo = servico.getResponseCode();
            logger.info("RESPONSE CODE: " + codigo);

            final String payload = codigo == 200
                    ? fromInputStream(servico.getInputStream())
                    : fromInputStream(servico.getErrorStream());
            return payload;
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }

    public String profissional(final String cns) {
        String retorno = profissional(ehr, token, cns, requisitante);

        // Se token expirado, obtenha token e tente novamente.
        if (retorno.contains("JWT expired")) {
            token();
            return profissional(ehr, token, cns, requisitante);
        }

        return retorno;
    }

    public static String cpf(String srv, String token, String cpf,
                             String authorization) {

        try {
            final String FMT = srv +
                    "Practitioner?identifier=http%3A%2F%2Frnds.saude" +
                    ".gov" +
                    ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" + cpf;
            logger.info("URL: " + FMT);

            final URL url = new URL(FMT);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", authorization);

            final int codigo = servico.getResponseCode();
            logger.warning("RESPONSE CODE: " + codigo);

            if (codigo != 200) {
                logger.warning(fromInputStream(servico.getErrorStream()));
                return null;
            }

            return fromInputStream(servico.getInputStream());
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }

    public String cpf(final String cpf) {
        return cpf(ehr, token, cpf, requisitante);
    }

    public String lotacao(final String cns, final String cnes) {
        String dst = ehr + "PractitionerRole" +
                "?practitioner.identifier=http%3A%2F%2Frnds.saude.gov" +
                ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" + cns +
                "&organization.identifier=http%3A%2F%2Frnds.saude.gov" +
                ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" + cnes +
                "&_include=PractitionerRole%3Aorganization";

        try {
            final URL url = new URL(dst);
            HttpsURLConnection servico =
                    (HttpsURLConnection) url.openConnection();
            servico.setRequestMethod("GET");
            servico.setRequestProperty("Content-Type", "application/json");
            servico.setRequestProperty("X-Authorization-Server",
                    "Bearer " + token);
            servico.setRequestProperty("Authorization", requisitante);

            final int codigo = servico.getResponseCode();
            logger.info("RESPONSE CODE: " + codigo);

            final String payload = codigo == 200
                    ? fromInputStream(servico.getInputStream())
                    : fromInputStream(servico.getErrorStream());
            return payload;
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
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
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }
}
