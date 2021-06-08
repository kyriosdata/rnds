/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de obtenção de token de acesso empregando certificado
 * disponibilizado pelo projeto segurança. Arquivo .zip disponível em
 * http://mobileapps.saude.gov
 * .br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58
 * /53c86213276e091be7128abc031f5d38_8ymqlifr9.zip
 *
 * <p>Estes testes ilustra necessidade de inclusão do certificado
 * da autoridade certificadora que produziu o certificado empregado pela
 * RNDS.</p>
 */
public class AccessTokenTest {

    private static final String RNDS_AUTH =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    private static final boolean DEBUG = false;

    private static RNDS rnds;

    @BeforeAll
    static void setUp() {
        if (!DEBUG) {
            Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
            Logger.getLogger("httpclient").setLevel(Level.OFF);
            Logger.getLogger("RNDS").setLevel(Level.OFF);
        } else {
            System.setProperty("javax.net.debug", "all");
        }

        rnds = new RNDSBuilder().build();
    }

    @Test
    public void keystoreOriginalWithoutLetsEncryptCertificate() {
        final String keystore = fromResource("certificado.jks");
        final String senha = "secret";
        assertNull(AccessToken.get(RNDS_AUTH, keystore, senha.toCharArray()));
    }

    @Test
    public void keystoreAdicionadoDeLetsEncryptCertificate() {
        final String keystore = fromResource("adicionado.jks");
        final char[] senha = "secret".toCharArray();
        final String token = AccessToken.get(RNDS_AUTH, keystore,
                senha);
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
}


