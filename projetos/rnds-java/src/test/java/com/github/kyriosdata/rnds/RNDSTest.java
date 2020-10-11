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
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        assertNotNull(rnds);
    }

    @Test
    public void recuperarTokenViaVariaveisDeAmbiente() {
        final String token = rnds.token();

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
    void uriParaObterToken() {
        assertTrue(rnds.forAuth().endsWith("/api/token"));
        assertTrue(rnds.forAuth().contains("https"));
    }

    @Test
    void uriParaContexto() {
        String ca = rnds.forEhr("api/contexto-atendimento");
        assertTrue(ca.contains("https"));
        assertTrue(ca.endsWith("/api/contexto-atendimento"));
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
    void profissionalPeloCnsPosteriormenteCpf() {
        String requisitanteCns = System.getenv("RNDS_REQUISITANTE_CNS");
        String retorno = rnds.profissional(requisitanteCns);

        // Parse json retornado
        final Any json = JsonIterator.deserialize(retorno);
        assertEquals(requisitanteCns, json.get("id").toString());

        // Recupera o CPF do requisitante para realizar próximo teste
        List<String> cpfs = json.get("identifier").asList()
                .stream()
                .filter(i -> i.get("system").toString().endsWith("/cpf"))
                .map(c -> c.get("value").toString())
                .collect(Collectors.toList());

        // Verifica a disponibilidade de um CPF (aquele do requisitante)
        assertEquals(1, cpfs.size());

        // Realiza busca por CPF
        String buscaPorCpf = rnds.cpf(cpfs.get(0));
        final Any jsonCpf = JsonIterator.deserialize(buscaPorCpf);
        assertEquals(1, jsonCpf.get("total").toInt());
    }

    @Test
    public void lotacao() {
        String requisitanteCns = System.getenv("RNDS_REQUISITANTE_CNS");
        String retorno = rnds.lotacao(requisitanteCns, "2337991");

        // Verifica retorno
        final Any json = JsonIterator.deserialize(retorno);
        assertEquals(1, json.get("total").toInt());
    }

    @Test
    public void cnpj() {
        final String cnpjUFG = "01567601000143";
        final String retorno = rnds.cnpj(cnpjUFG);

        // Verifica retorno
        final Any json = JsonIterator.deserialize(retorno);
        assertEquals(cnpjUFG, json.get("id").toString());
        final String name = json.get("name").toString();
        assertEquals("UNIVERSIDADE FEDERAL DE GOIAS", name);

    }
}