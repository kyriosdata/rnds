/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Classe para acesso aos serviços oferecidos pela RNDS. Tanto o ambiente
 * de homologação quanto o de produção estão contemplados.
 */
public class RNDS {

    static final Logger logger = Logger.getLogger("RNDS");

    /**
     * Endereço dos serviços de saúde.
     */
    private final String ehr;

    /**
     * Cache do requisitante (CNS) em nome do qual requisições serão
     * realizadas, se não dito o contrário por cada requisição.
     */
    private final String requisitante;
    /**
     * Unidade da Federação na qual está localizado o estabelecimento de saúde.
     */
    private final Estado estado;

    /**
     * <i>Token</i> de acesso aos serviços da RNDS. Este valor é reutilizado
     * por cerca de 30 minutos. Após este período, deve ser renovado.
     */
    private AccessToken tokenCache;

    /**
     * Cria objeto por meio do qual interação com a RNDS é encapsulada por
     * meio de simples operações.
     *
     * <p>A estratégia sugerida para obtenção de uma instância, contudo,
     * é pela classe {@link RNDSBuilder}.</p>
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
     * @see RNDSBuilder
     */
    public RNDS(final String auth,
                final String ehr,
                final String keystore,
                final char[] password,
                final String requisitante,
                final Estado estado) {
        // AUTH (prefixo https://) (sufixo /api/)

        Objects.requireNonNull(ehr, "ehr");
        this.ehr = ehr;
        this.requisitante = Objects.requireNonNull(requisitante,
                "requisitante");
        this.estado = Objects.requireNonNull(estado, "estado");

        // Cache token para uso na primeira requisição
        Objects.requireNonNull(auth, "auth");
        Objects.requireNonNull(keystore, "keystore");
        Objects.requireNonNull(password, "password");
        final String url = String.format("https://%s/api/token", auth);
        tokenCache = new AccessToken(url, keystore, password);
    }

    /**
     * Método de conveniência para obter uma sequência de caracteres,
     * no formato UTF-8, a partir da entrada fornecida.
     *
     * @param is Entrada da qual o conteúdo será obtido.
     * @return Sequência de caracteres disponível na entrada ou valor
     * {@code null}, em caso de erro.
     */
    static String fromInputStream(final InputStream is) {
        try {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                is.close();
                return baos.toString(StandardCharsets.UTF_8);
            }
        } catch (IOException exception) {
            logger.warning("EXCECAO: " + exception);
            return null;
        }
    }

    /**
     * Obtém a URI para acesso aos serviços EHR oferecidos pela RNDS.
     *
     * @param path O path da URI, não inicia por "/" nem termina por "/".
     * @return A URI para o serviço cujo path é fornecido.
     */
    public String forEhr(String path) {
        return String.format("https://%s/%s", ehr, path);
    }

    /**
     * Obtém a URI para acesso aos serviços EHR oferecidos pela RNDS.
     *
     * @param path  O path da URI, não inicia nem termina por "/".
     * @param query A query empregada para acesso ao serviço, necessariamente
     *              inicia por "?". Caso contrário, será tratado como
     *              complemento e deve seguir o path, iniciando por "/".
     * @return A URI para acesso ao serviço montado com os parâmetros
     * fornecidos.
     */
    public String forEhr(String path, String query) {
        if ("".equals(query)) {
            return forEhr(path);
        }

        return String.format("https://%s/%s/%s", ehr, path, query);
    }

    public HttpsURLConnection connection(
            final String method,
            final String path,
            final String query,
            final String payload) throws IOException {
        final String uri = forEhr(path, query);
        logger.info("SERVICO: " + uri);

        final URL url = new URL(uri);
        HttpsURLConnection servico =
                (HttpsURLConnection) url.openConnection();
        servico.setRequestMethod(method);
        servico.setRequestProperty("Content-Type", "application/json");

        final String token = tokenCache.token();
        servico.setRequestProperty("X-Authorization-Server", "Bearer " + token);
        servico.setRequestProperty("Authorization", requisitante);
        servico.setInstanceFollowRedirects(true);

        if ("POST".equals(method)) {
            servico.setDoOutput(true);

            // Fornece payload
            final OutputStream os = servico.getOutputStream();
            os.write(payload.getBytes(StandardCharsets.UTF_8));
            os.close();
        }

        return servico;
    }

    private Resposta send(
            final String method,
            final String path,
            final String query,
            final String payload) {
        try {
            final HttpsURLConnection servico = connection(method, path,
                    query, payload);

            final int codigo = servico.getResponseCode();
            final String msg = (codigo != 200) && (codigo != 201)
                    ? fromInputStream(servico.getErrorStream())
                    : fromInputStream(servico.getInputStream());

            Resposta answer = new Resposta(codigo, msg,
                    servico.getHeaderFields());
            servico.disconnect();
            return answer;
        } catch (IOException exception) {
            return new Resposta(599, exception.getMessage(),
                    Collections.emptyMap());
        }
    }

    public Resposta contexto(final String cnes,
                             final String profissional,
                             final String paciente) {
        final String payload = String.format("{\"cnes\":\"%s\"," +
                        "\"cnsProfissional\":\"%s\",\"cnsPaciente\":\"%s\"}",
                cnes, profissional, paciente);
        return send("POST", "api/contexto-atendimento", "", payload);
    }

    public Resposta cnes(final String cnes) {
        return send("GET", "api/fhir/r4/Organization", cnes, null);
    }

    public Resposta cns(final String cns) {
        return send("GET", "api/fhir/r4/Practitioner", cns, null);
    }

    public Resposta cpf(String cpf) {
        final String query =
                "?identifier=http%3A%2F%2Frnds.saude.gov" +
                        ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" + cpf;
        return send("GET", "api/fhir/r4/Practitioner", query, null);
    }

    public Resposta lotacao(final String cns, final String cnes) {
        final String dst =
                "?practitioner.identifier=http%3A%2F%2Frnds.saude.gov" +
                        ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcns%7C" + cns +
                        "&organization.identifier=http%3A%2F%2Frnds.saude.gov" +
                        ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcnes%7C" + cnes +
                        "&_include=PractitionerRole%3Aorganization";
        return send("GET", "api/fhir/r4/PractitionerRole", dst, null);
    }

    public Resposta cnpj(final String cnpj) {
        return send("GET", "api/fhir/r4/Organization", cnpj, null);
    }

    public Resposta paciente(final String cpf) {
        final String query =
                "?identifier=http%3A%2F%2Frnds.saude.gov" +
                        ".br%2Ffhir%2Fr4%2FNamingSystem%2Fcpf%7C" + cpf;
        return send("GET", "api/fhir/r4/Patient", query, null);
    }

    public Resposta notificar(final String exame) {
        Resposta resposta = send("POST", "api/fhir/r4/Bundle", "", exame);

        // Se não foi possível criar a notificação, então retorne a resposta.
        if (resposta.code != 201) {
            return resposta;
        }

        // Se foi possível criar a notificação, então o retorno esperado
        // é o identificador atribuído pela RNDS ao resultado notificado.
        final String rndsId = getRndsId(resposta.headers);
        return new Resposta(resposta.code, rndsId, resposta.headers);
    }

    private String getRndsId(Map<String, List<String>> headers) {
        final String location = headers.get("location").get(0);
        final int idx = location.indexOf("Bundle/");
        return location.substring(idx + 7);
    }

    public Resposta substituir(final String alteracao) {
        return notificar(alteracao);
    }

    /**
     * Unidades da Federação do Brasil.
     */
    public enum Estado {
        AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA,
        PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO
    }

    static class Resposta {
        public final int code;
        public final String retorno;
        public final Map<String, List<String>> headers;

        public Resposta(int code, String retorno,
                        Map<String, List<String>> headers) {
            this.code = code;
            this.retorno = retorno;
            this.headers = headers;
        }
    }
}
