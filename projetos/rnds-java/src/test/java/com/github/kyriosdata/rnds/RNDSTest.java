/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;
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
    private static final boolean DEBUG = false;

    private static RNDS rnds;

    private static String exame;

    private static String exameSubstituir;

    private static String requisitante =
            System.getenv("RNDS_REQUISITANTE_CNS");

    @BeforeAll
    static void obtemConfiguracao() throws FileNotFoundException {
        if (!DEBUG) {
            Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
            Logger.getLogger("httpclient").setLevel(Level.OFF);
            Logger.getLogger("RNDS").setLevel(Level.OFF);
        } else {
            System.setProperty("javax.net.debug", "all");
        }

        rnds = new RNDSBuilder().build();
        assertNotNull(rnds);

        exame = getConteudo("exame.json");
        exameSubstituir = getConteudo("exame-substituir.json");
    }

    private static String getConteudo(String arquivo1) throws FileNotFoundException {
        final String arquivo = fromResource(arquivo1);
        final InputStream fis = new FileInputStream(arquivo);
        return RNDS.fromInputStream(fis);
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
        final RNDS.Resposta resposta = rnds.cnes(cnes);
        assertEquals(200, resposta.code);

        // Parse json retornado
        final Any json = JsonIterator.deserialize(resposta.retorno);

        // Verifica propriedade "id"
        final String id = json.get("id").toString();
        assertEquals(cnes, id);

        // Verifica nome do estabelecimento de saúde
        final String name = json.get("name").toString();
        assertEquals("LABORATORIO ROMULO ROCHA", name);
    }

    @Test
    void cnesInvalidoNaoPodeSerEncontrado() {
        RNDS.Resposta resposta = rnds.cnes("invalido");
        assertEquals(404, resposta.code);

        // Parse json retornado
        final Any json = JsonIterator.deserialize(resposta.retorno);

        final String resourceType = json.get("resourceType").toString();
        assertEquals("OperationOutcome", resourceType);
    }

    @Test
    void cpf() {
        final RNDS.Resposta resposta = rnds.cpf("1234");
        assertEquals(200, resposta.code);
    }

    @Test
    public void lotacao() {
        RNDS.Resposta resposta = rnds.lotacao(requisitante, "2337991");
        assertEquals(200, resposta.code);

        // Verifica resposta
        final Any json = JsonIterator.deserialize(resposta.retorno);
        assertTrue(json.get("total").toInt() > 0);
    }

    @Test
    public void cnpj() {
        final String cnpjUFG = "01567601000143";
        final RNDS.Resposta retorno = rnds.cnpj(cnpjUFG);
        assertEquals(200, retorno.code);

         // Verifica retorno
        final Any json = JsonIterator.deserialize(retorno.retorno);
        assertEquals(cnpjUFG, json.get("id").toString());
        final String name = json.get("name").toString();
        assertEquals("UNIVERSIDADE FEDERAL DE GOIAS", name);
    }

    @Test
    void contexto() {
        final RNDS.Resposta resposta = rnds.contexto("2337991",
                requisitante, requisitante);
        assertEquals(200, resposta.code, resposta.retorno);
    }

    @Test
    void contextoSemCnesFalha() {
        final RNDS.Resposta resposta = rnds.contexto("cnes",
                requisitante, requisitante);
        assertEquals(500, resposta.code, resposta.retorno);
    }

    @Test
    void contextoSemProfissionalFalha() {
        final RNDS.Resposta resposta = rnds.contexto("2337991",
                "p", requisitante);
        assertEquals(422, resposta.code, resposta.retorno);
    }

    @Test
    void contextoSemPacienteFalha() {
        final RNDS.Resposta resposta = rnds.contexto("2337991",
                requisitante, "p");
        assertEquals(422, resposta.code, resposta.retorno);
    }

    @Test
    void cnsInvalidoNaoLocalizado() {
        final RNDS.Resposta resposta = rnds.cns("123");
        assertEquals(404, resposta.code);
    }

    @Test
    void cns() {
        final RNDS.Resposta resposta = rnds.cns(requisitante);
        assertEquals(200, resposta.code);
    }

    @Test
    void paciente() {
        final RNDS.Resposta resposta = rnds.paciente("123");
        assertEquals(200, resposta.code);
    }

    @Test
    void notificar() {
        final String labId = UUID.randomUUID().toString();
        final String atualizada = exame.replace("{{lab-id}}", labId);

        final RNDS.Resposta resposta = rnds.notificar(atualizada);
        assertEquals(201, resposta.code, resposta.retorno);
        assertFalse(resposta.retorno.isEmpty());
    }

    @Test
    void substituir() throws InterruptedException {
        final String labId = UUID.randomUUID().toString();
        final String atualizada = exame.replace("{{lab-id}}", labId);
        final RNDS.Resposta resposta = rnds.notificar(atualizada);
        assertEquals(201, resposta.code, resposta.retorno);

        final String rndsId = resposta.retorno;

        final String lab = exameSubstituir.replace("{{lab-id}}", labId);
        final String montada = lab.replace("{{rnds-id}}", rndsId);
        assertTrue(montada.contains(rndsId));

        // Parece que é necessário "dar um tempo" para o servidor
        // reconhecer a notificação criada. Ou seja, a atualização
        // imediata faz com que a notificação original não seja reconhecida.
        Thread.sleep(3000);
        
        final RNDS.Resposta substituicao = rnds.substituir(montada);
        assertEquals(201, substituicao.code, substituicao.retorno);
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