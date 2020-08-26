/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RNDSTest {

    private static final String RNDS_CERTIFICADO_SENHA = "secret";
    private static final String RNDS_CERTIFICADO_ARQUIVO = "certificado.jks";

    private static final String RNDS_AUTENTICADOR =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    private static final String RNDS_SERVICOS =
            "https://ehr-services-hmg.saude.gov.br/api/";

    private static String certificadoSenha;
    private static String certificadoArquivo;
    private static String autenticador;
    private static String servicos;

    @BeforeAll
    static void setup() {
        autenticador = System.getenv("RNDS_AUTENTICADOR");
        servicos = System.getenv("RNDS_SERVICOS");
        certificadoArquivo = System.getenv("RNDS_CERTIFICADO_ARQUIVO");
        certificadoSenha = System.getenv("RNDS_CERTIFICADO_SENHA");
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
    void recuperarToken() {
        String arquivo = fromResource(RNDS_CERTIFICADO_ARQUIVO);
        char[] keyStorePassword = RNDS_CERTIFICADO_SENHA.toCharArray();
        String token = RNDS.getToken(RNDS_AUTENTICADOR, arquivo, keyStorePassword);
        assertEquals(2334, token.length());
    }
}


