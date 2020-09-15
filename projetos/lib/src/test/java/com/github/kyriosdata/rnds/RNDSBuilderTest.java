/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RNDSBuilderTest {

    @Test
    public void authNaoDefinido() {
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().build());
        assertTrue(retorno.getMessage().contains("auth"));
    }

    @Test
    public void ehrNaoDefinido() {
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").build());
        assertTrue(retorno.getMessage().contains("ehr"));
    }

    @Test
    public void keystoreNaoDefinido() {
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr").build());
        assertTrue(retorno.getMessage().contains("keystore"));
    }

    @Test
    public void passowrdNaoDefinido() {
        final String keystore = fromResource("certificado.jks");

        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .keystore(keystore)
                        .build());
        assertTrue(retorno.getMessage().contains("password"));
    }

    @Test
    public void requisitanteNaoDefinido() {
        final String keystore = fromResource("certificado.jks");

        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .keystore(keystore)
                        .password("senha".toCharArray())
                        .build());
        assertTrue(retorno.getMessage().contains("requisitante"));
    }

    @Test
    public void estadoNaoDefinido() {
        final String keystore = fromResource("certificado.jks");

        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .keystore(keystore)
                        .password("senha".toCharArray())
                        .requisitante("requisitante")
                        .build());
        assertTrue(retorno.getMessage().contains("estado"));
    }

    @Test
    public void construcaoEsperadaTodosArgumentosExigidosFornecidos() {
        final String keystore = fromResource("certificado.jks");

        new RNDSBuilder().auth("auth").ehr("ehr")
                .keystore(keystore)
                .password("senha".toCharArray())
                .requisitante("requisitante")
                .estado(RNDS.Estado.AC)
                .build();
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
}
