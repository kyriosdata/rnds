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
                () -> new RNDSBuilder().auth(null).build());
        assertTrue(retorno.getMessage().contains("auth"));
    }

    @Test
    public void ehrNaoDefinido() {
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().ehr(null).auth("auth").build());
        assertTrue(retorno.getMessage().contains("ehr"));
    }

    @Test
    public void keystoreNaoDefinido() {
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder()
                        .keystore(null).auth("auth").ehr("ehr").build());
        assertTrue(retorno.getMessage().contains("keystore"));
    }

    @Test
    public void passowrdNaoDefinido() {
        final String keystore = fromResource("certificado.jks");

        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .password(null)
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
                        .requisitante(null)
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
                .estado("AC")
                .build();
    }

    @Test
    public void homologacaoSemVariaveisDeAmbiente() {
        final String keystore = fromResource("certificado.jks");

        new RNDSBuilder().keystore(keystore).password(new char[]{})
                .requisitante("requisitante").estado("AC")
                .homologacao();
    }

    @Test
    public void homologacaoAuthEhrPadrao() {
        final String keystore = fromResource("certificado.jks");

        new RNDSBuilder().keystore(keystore).password(new char[]{})
                .auth(null)
                .ehr(null)
                .requisitante("requisitante").estado("AC")
                .homologacao();
    }

    @Test
    public void producaoAuthEhrPadrao() {
        final String keystore = fromResource("certificado.jks");

        new RNDSBuilder().keystore(keystore).password(new char[]{})
                .auth(null)
                .ehr(null)
                .requisitante("requisitante").estado("AC")
                .producao();
    }

    @Test
    public void producaoAuthEhrPadraoSemEstadoFalha() {
        final String keystore = fromResource("certificado.jks");

        Throwable excecao = assertThrows(NullPointerException.class, () ->
                new RNDSBuilder().keystore(keystore).password(new char[]{})
                        .auth(null)
                        .ehr(null)
                        .requisitante("requisitante")
                        .producao());

        assertTrue(excecao.getMessage().contains("estado"));
    }

    @Test
    public void homologacaoUsaKeystoreEnvironment() {
        new RNDSBuilder().password(new char[]{})
                .requisitante("requisitante").estado("AC")
                .homologacao();
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
