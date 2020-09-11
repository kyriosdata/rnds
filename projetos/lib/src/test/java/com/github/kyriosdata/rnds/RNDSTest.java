/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Base64;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A execução satisfatória dos testes depende de valores para as
 * seguintes variáveis de ambiente:
 *
 * <ul>
 *     <li><b>RNDS_AUTH</b>: endereço do serviço de autenticação.</li>
 *     <li><b>RNDS_EHR</b>: endereço do serviço de saúde.</li>
 *     <li><b>RNDS_CERTIFICADO_ARQUIVO</b>: caminho completo do arquivo
 *     contendo o certificado do laboratório.</li>
 *     <li><b>RNDS_CERTIFICADO_SENHA</b>: senha para acesso ao conteúdo do
 *     certificado digital.</li>
 * </ul>
 */
public class RNDSTest {

    private static final String RNDS_CERTIFICADO_SENHA = "secret";
    private static final String RNDS_CERTIFICADO_ARQUIVO = "certificado.jks";

    private static final String RNDS_AUTH =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    private static final String RNDS_EHR =
            "https://ehr-services-hmg.saude.gov.br/api/";

    private static char[] password;
    private static String keystore;
    private static String auth;
    private static String ehr;

    @BeforeAll
    static void obtemConfiguracao() {
        // System.setProperty("javax.net.debug", "all");
        auth = System.getenv("RNDS_AUTH");
        assertNotNull(auth, "Auth não definido");
        System.out.println(auth);

        ehr = System.getenv("RNDS_EHR");
        assertNotNull(ehr, "EHR não definido");
        System.out.println(ehr);

        keystore = System.getenv("RNDS_CERTIFICADO_ARQUIVO");
        assertTrue(Files.exists(Path.of(keystore)), "arquivo com " +
                "certificado inexistente");
        System.out.println(keystore);

        final String senha = System.getenv("RNDS_CERTIFICADO_SENHA");
        assertNotNull(senha, "senha de acesso ao certificado null");
        assertNotEquals("", senha.trim(), "senha vazia");
        password = senha.toCharArray();
        System.out.println(senha);
    }

    @Test
    public void keystoreOriginalWithoutLetsEncryptCertificate() {
        final String keystore = fromResource("certificado.jks");
        final String senha = "secret";
        assertNull(RNDS.getToken(RNDS_AUTH, keystore, senha.toCharArray()));
    }

    @Test
    public void keystoreAdicionadoDeLetsEncryptCertificate() {
        final String keystore = fromResource("adicionado.jks");
        final char[] senha = "secret".toCharArray();
        final String token = RNDS.getToken(RNDS_AUTH, keystore, senha);
        assertNotNull(token);
        assertEquals(2334, token.length());
    }

    /**
     * Obtém path completo do nome do arquivo fornecido que se encontra
     * no diretório resources.
     *
     * @param arquivo Nome do arquivo contido no diretório "resources".
     * @return O caminho completo para o arquivo cujo nome é fornecido.
     */
    static String fromResource(final String arquivo) {
        Class<RNDS> appClass = RNDS.class;
        return appClass.getClassLoader().getResource(arquivo).getPath();
    }

    @Test
    public void recuperarTokenViaVariaveisDeAmbiente() {
        final String token = RNDS.getToken(auth, keystore, password);
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];

        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        assertTrue(header.contains("kid"));
        assertTrue(header.contains("rnds auth"));
        assertTrue(header.contains("RS256"));


        String body = new String(base64Url.decode(base64EncodedBody));
        assertTrue(body.contains("RNDS-HMG"));
        assertTrue(body.contains("ICP-Brasil"));
    }

    @Test
    void cnesConhecido() {
        final String token = RNDS.getToken(auth, keystore, password);
        String cnes = RNDS.cnes(ehr, token, "2337991", "980016287385192");
        assertTrue(cnes.contains("LABORATORIO ROMULO ROCHA"));
    }

    @Test
    void cnesInvalidoNaoPodeSerEncontrado() {
        final String token = RNDS.getToken(auth, keystore, password);
        assertNull(RNDS.cnes(ehr, token, "233799", "980016287385192"));
    }

    @Test
    void profissionalPeloCns() {
        final String token = RNDS.getToken(auth, keystore, password);
        String cns = RNDS.profissional(ehr, token, "980016287385192",
                "980016287385192");
        assertTrue(cns.contains("SANTOS"));
    }

    @Test
    void profissionalPeloCpf() {
        final String token = RNDS.getToken(auth, keystore, password);
        String cns = RNDS.cpf(ehr, token, "01758263156",
                "980016287385192");
        assertTrue(cns.contains("SANTOS"));
    }
}


