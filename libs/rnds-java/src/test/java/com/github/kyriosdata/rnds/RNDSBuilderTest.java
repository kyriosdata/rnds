/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.kyriosdata.rnds.RNDSBuilder.RNDS_CERTIFICADO_ENDERECO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RNDSBuilderTest {

    private static String keystore;

    @BeforeAll
    public static void before() {
        keystore = System.getenv(RNDS_CERTIFICADO_ENDERECO);
    }

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
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .password(null)
                        .keystore(keystore)
                        .build());
        assertTrue(retorno.getMessage().contains("password"));
    }

    @Test
    public void requisitanteNaoDefinido() {
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
        Throwable retorno = assertThrows(NullPointerException.class,
                () -> new RNDSBuilder().auth("auth").ehr("ehr")
                        .keystore(keystore)
                        .password("senha".toCharArray())
                        .requisitante("requisitante")
                        .estado(null)
                        .build());
        assertTrue(retorno.getMessage().contains("estado"));
    }

    @Test
    public void construcaoEsperadaTodosArgumentosExigidosFornecidos() {
        new RNDSBuilder().auth("auth").ehr("ehr")
                .keystore(keystore)
                .password("senha".toCharArray())
                .requisitante("requisitante")
                .estado("AC")
                .build();
    }

    @Test
    public void homologacaoSemVariaveisDeAmbiente() {
                new RNDSBuilder().keystore(keystore).password(new char[]{})
                .requisitante("requisitante").estado("AC")
                .homologacao();
    }

    @Test
    public void homologacaoAuthEhrPadrao() {
        new RNDSBuilder().keystore(keystore).password(new char[]{})
                .auth(null)
                .ehr(null)
                .requisitante("requisitante").estado("AC")
                .homologacao();
    }

    @Test
    public void producaoAuthEhrPadrao() {
        new RNDSBuilder().keystore(keystore).password(new char[]{})
                .auth(null)
                .ehr(null)
                .requisitante("requisitante").estado("AC")
                .producao();
    }

    @Test
    public void producaoAuthEhrPadraoSemEstadoFalha() {
        Throwable excecao = assertThrows(NullPointerException.class, () ->
                new RNDSBuilder().keystore(keystore).password(new char[]{})
                        .auth(null)
                        .ehr(null)
                        .requisitante("requisitante")
                        .estado(null)
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
