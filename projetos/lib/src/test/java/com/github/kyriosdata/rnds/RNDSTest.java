/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class RNDSTest {

    private static final String RNDS_CERTIFICADO_SENHA = "secret";
    private static final String RNDS_CERTIFICADO_ARQUIVO = "certificado.jks";

    private static final String RNDS_AUTENTICADOR =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    private static final String RNDS_SERVICOS =
            "https://ehr-services-hmg.saude.gov.br/api/";

    private static char[] certificadoSenha;
    private static String certificadoArquivo;
    private static String autenticador;
    private static String servicos;

    @BeforeAll
    static void setup() {
        autenticador = System.getenv("RNDS_AUTENTICADOR");
        servicos = System.getenv("RNDS_SERVICOS");
        certificadoArquivo = System.getenv("RNDS_CERTIFICADO_ARQUIVO");
        String senha = System.getenv("RNDS_CERTIFICADO_SENHA");
        certificadoSenha = senha != null ? senha.toCharArray() : null;
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
    void variaveisDeAmbienteDefinidas() {
        assertNotNull(autenticador, "autenticador null");
        assertNotNull(servicos, "url servicos desconhecida");
        assertNotNull(certificadoArquivo, "arquivo cert nao definido");
        assertNotNull(certificadoSenha, "senha certificado unknown");
    }

    @Test
    void recuperarTokenExemploRnds() {
        String arquivo = fromResource(RNDS_CERTIFICADO_ARQUIVO);
        char[] keyStorePassword = RNDS_CERTIFICADO_SENHA.toCharArray();
        String token = RNDS.getToken(RNDS_AUTENTICADOR, arquivo,
                keyStorePassword);
        assertEquals(2334, token.length());
    }

    @Test
    void recuperarTokenViaVariaveisDeAmbiente() {
        String token = RNDS.getToken(
                RNDS_AUTENTICADOR, certificadoArquivo, certificadoSenha);
        assertTrue(token.length() > 2048);
    }

    @Test
    public void testDecodeJWT(){
        String jwtToken = RNDS.getToken(
                RNDS_AUTENTICADOR, certificadoArquivo, certificadoSenha);
        String[] split_string = jwtToken.split("\\.");
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
}


