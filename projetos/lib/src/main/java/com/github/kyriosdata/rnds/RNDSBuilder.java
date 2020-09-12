package com.github.kyriosdata.rnds;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Cria uma instância de {@link RNDS} devidamente configurada para
 * submeter requisições para um determinado estabelecimento de saúde.
 */
public class RNDSBuilder {
    private String auth;
    private String ehr;
    private String keystore;
    private char[] password;
    private String requisitante;
    private RNDS.Estado estado;

    /**
     * Define o <i>host</i> do serviço de autenticação da RNDS.
     *
     * @param auth Nome do domínio (endereço) do serviço de autenticação da
     *             RNDS.
     *
     * @return RNDSBuilder com endereço do serviço de autenticação definido.
     */
    public RNDSBuilder auth(final String auth) {
        this.auth = auth;
        return this;
    }

    /**
     * Define o <i>host</i> dos serviços de saúde da RNDS.
     *
     * @param ehr Nome do domínio (endereço) do serviço de autenticação da
     *             RNDS.
     *
     * @return RNDSBuilder com endereço dos serviços de saúde definido.
     */
    public RNDSBuilder ehr(final String ehr) {
        this.ehr = ehr;
        return this;
    }

    /**
     * Define o caminho para o arquivo contendo o certificado digital do
     * estabelecimento de saúde. O certificado digital é armazenado em um
     * arquivo, em geral, com a extensão <b>.pfx</b>.
     *
     * @param keystore Caminho para o arquivo contendo o certificado digital
     *                 do estabelecimento de saúde.
     *
     * @return RNDSBuilder definido com o caminho do arquivo do certificado
     * digital do estabelecimento de saúde.
     */
    public RNDSBuilder keystore(final String keystore) {
        if (!Files.exists(Paths.get(keystore))) {
            throw new IllegalArgumentException("keystore não existe");
        }

        this.keystore = keystore;
        return this;
    }

    /**
     * Define a senha de acesso ao conteúdo do certificado digital (o arquivo
     * contendo o certificado digital, ou <i>keystore</i>).
     *
     * @param password Senha de acesso ao certificado digital (arquivo
     *                 <i>keystore</i>.
     *
     * @return RNDSBuilder definido com a senha de acesso ao conteúdo do
     * certificado digital.
     */
    public RNDSBuilder password(final char[] password) {
        this.password = password;
        return this;
    }

    /**
     * Define o requisitante, por meio do seu CNS, em nome do qual as
     * requisições serão enviadas. O requisitante necessariamente deve ser
     * um profissional de saúde lotado no estabelecimento de saúde.
     *
     * @param requisitante O CNS do profissional de saúde, lotado no
     *                     estabelecimento de saúde em questão, em nome do
     *                     qual as requisições serão enviadas.
     *
     * @return RNDSBuilder definido com o requisitante.
     */
    public RNDSBuilder requisitante(final String requisitante) {
        this.requisitante = requisitante;
        return this;
    }

    /**
     * Define o estado no qual reside o estabelecimento de saúde. Este estado
     * deve estar em conformidade com o cadastro CNES do estabelecimento de
     * saúde.
     * @param estado
     * @return
     */
    public RNDSBuilder estado(RNDS.Estado estado) {
        this.estado = estado;
        return this;
    }

    public RNDS build() {
        return new RNDS(auth, ehr, keystore, password, requisitante, estado);
    }
}