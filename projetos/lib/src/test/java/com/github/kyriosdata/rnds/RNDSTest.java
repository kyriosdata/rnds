/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private static String token;

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

        rnds = new RNDSBuilder().build();
    }

    @BeforeEach
    public void obtemToken() {
        token = rnds.token();
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
        final String cnes = "2337991";

        // Submete requisição à RNDS
        final String retorno = rnds.cnes(cnes);

        // Parse json retornado
        final Any json = JsonIterator.deserialize(retorno);

        // Verifica propriedade "id"
        final String id = json.get("id").toString();
        assertEquals(cnes, id);

        // Verifica nome do estabelecimento de saúde
        final String name = json.get("name").toString();
        assertEquals("LABORATORIO ROMULO ROCHA", name);
    }

    @Test
    void cnesInvalidoNaoPodeSerEncontrado() {
        String retorno = rnds.cnes("invalido");

        // Parse json retornado
        final Any json = JsonIterator.deserialize(retorno);

        final String resourceType = json.get("resourceType").toString();
        assertEquals("OperationOutcome", resourceType);
    }

    @Test
    void profissionalPeloCns() {
        String requisitanteCns = System.getenv("RNDS_REQUISITANTE_CNS");
        String retorno = rnds.profissional(requisitanteCns);
        assertTrue(retorno.contains("SANTOS"));
    }

    @Test
    void profissionalPeloCpf() {
        String retorno = rnds.cpf("01758263156");
        assertTrue(retorno.contains("SANTOS"));
    }
}