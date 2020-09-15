/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Base64;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A execução satisfatória dos testes depende de valores para as
 * seguintes variáveis de ambiente:
 *
 * <ul>
 *     <li><b>RNDS_AUTH</b>: endereço do serviço de autenticação.</li>
 *     <li><b>RNDS_EHR</b>: endereço do serviço de saúde.</li>
 *     <li><b>RNDS_CERTIFICADO_ENDERECO</b>: caminho completo do arquivo
 *     contendo o certificado do laboratório.</li>
 *     <li><b>RNDS_CERTIFICADO_SENHA</b>: senha para acesso ao conteúdo do
 *     certificado digital.</li>
 *     <li><b>RNDS_REQUISITANTE_CNS</b>: CNS do profissional de saúde em
 *     nome do qual o serviço requisitado é submetido.</li>
 * </ul>
 */
public class RNDSTest {

    private static char[] password;
    private static String keystore;
    private static String auth;
    private static String ehr;
    private static String token;
    private static String individuoCns;

    private static final boolean DEBUG = false;

    private static RNDS rnds;

    @BeforeAll
    static void obtemConfiguracao() {
        if (!DEBUG) {
            Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
            Logger.getLogger("httpclient").setLevel(Level.OFF);
            Logger.getLogger("RNDS").setLevel(Level.OFF);
        } else {
            System.setProperty("javax.net.debug", "all");
        }

        // Serviço Auth
        auth = System.getenv("RNDS_AUTH");
        assertNotNull(auth, "Auth não definido");

        // Serviços EHR
        ehr = System.getenv("RNDS_EHR");
        assertNotNull(ehr, "EHR não definido");

        // Arquivo (certificado)
        keystore = System.getenv("RNDS_CERTIFICADO_ENDERECO");
        assertTrue(Files.exists(Path.of(keystore)), "arquivo com " +
                "certificado inexistente");

        // Senha de acesso ao conteúdo do certificado (keystore)
        final String senha = System.getenv("RNDS_CERTIFICADO_SENHA");
        assertNotNull(senha, "senha de acesso ao certificado null");
        assertNotEquals("", senha.trim(), "senha vazia");
        password = senha.toCharArray();

        individuoCns = System.getenv("RNDS_REQUISITANTE_CNS");
        assertNotNull(individuoCns, "responsável não fornecido");
        assertNotEquals("", individuoCns.trim());

        rnds = new RNDSBuilder().build();
    }

    @BeforeEach
    public void obtemToken() {
        token = RNDS.getToken(auth, keystore, password);
        assertNotNull(token);
    }

    @Test
    public void recuperarTokenViaVariaveisDeAmbiente() {
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
        String cnes = RNDS.cnes(ehr, token, "2337991", individuoCns);
        assertTrue(cnes.contains("LABORATORIO ROMULO ROCHA"));
    }

    @Test
    void cnesInvalidoNaoPodeSerEncontrado() {
        assertNull(RNDS.cnes(ehr, token, "233799", individuoCns));
    }

    @Test
    void profissionalPeloCns() {
        String cns = RNDS.profissional(ehr, token, individuoCns,
                individuoCns);
        assertTrue(cns.contains("SANTOS"));
    }

    @Test
    void profissionalPeloCpf() {
        String cns = RNDS.cpf(ehr, token, "01758263156",
                individuoCns);
        assertTrue(cns.contains("SANTOS"));
    }
}