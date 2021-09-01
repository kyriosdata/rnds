/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de autenticação nos serviços oferecidos pela RNDS.
 *
 * <p>Exige a definição correta das seguintes variáveis de ambinte:
 * <ul>
 *     <li>RNDS_AUTH</li>. Endereço do _endpoint_ empregado para autenticação.
 *     <li>RNDS_CERTIFICADO_ENDERECO</li>. Caminho completo do arquivo _keystore_.
 *     <li>RNDS_CERTIFICADO_SENHA</li>. Senha de acesso ao arquivo _keystore_.
 * </ul>
 * </p>
 *
 */
public class AccessTokenTest {
    private static final boolean DEBUG = false;

    private static final String RNDS_AUTH = "RNDS_AUTH";
    private static final String RNDS_CERTIFICADO_ENDERECO = "RNDS_CERTIFICADO_ENDERECO";
    private static final String RNDS_CERTIFICADO_SENHA = "RNDS_CERTIFICADO_SENHA";

    private static String URL = String.format("https://%s/api/token", System.getenv(RNDS_AUTH));
    private static String keystore = System.getenv(RNDS_CERTIFICADO_ENDERECO);
    private static String senha = System.getenv(RNDS_CERTIFICADO_SENHA);

    private static AccessToken cache;

    @BeforeAll
    static void setUp() {
        if (!DEBUG) {
            Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
            Logger.getLogger("httpclient").setLevel(Level.OFF);
            Logger.getLogger("RNDS").setLevel(Level.OFF);
        } else {
            System.setProperty("javax.net.debug", "all");
        }

        cache = new AccessToken(URL, keystore, senha.toCharArray());
    }

    @Test
    public void urlErradaProvocaFalha() {
        AccessToken cacheToken = new AccessToken(URL + "erro", keystore, senha.toCharArray());
        assertTrue(cacheToken.token().equals(""));
    }

    @Test
    void tokenRecuperadoDoCache() {
        final String token = cache.token();
        assertTrue(token.length() > 2000);

        final String novoToken = cache.token();
        assertSame(token, novoToken);
    }

    @Test
    void aposInvalidarNovoTokenRecuperado() throws InterruptedException {
        final String token = cache.token();
        assertTrue(token.length() > 2000);

        // Invalida cache (novo token deve ser recuperado)
        cache.invalidar();

        // Aguarda pelo menos 2 segundos, caso contrário,
        // o servidor irá retornar o mesmo valor.
        Thread.sleep(2 * 1000);

        final String novoToken = cache.token();
        assertTrue(novoToken.length() > 2000);

        assertNotSame(token, novoToken);
        assertNotEquals(token, novoToken);
    }

    @Test
    public void recuperarTokenViaVariaveisDeAmbiente() {
        final String token = cache.token();

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
}


