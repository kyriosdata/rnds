/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RNDSTest {

    public static final String AUTH = "https://ehr-auth-hmg.saude.gov" +
            ".br/api/token";

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
    void obtemArquivo() {
        String arquivo = fromResource("certificado.jks");
        char[] keyStorePassword = "secret".toCharArray();
        String token = RNDS.getToken(AUTH, arquivo, keyStorePassword);
        assertEquals(2334, token.length());
    }
}


