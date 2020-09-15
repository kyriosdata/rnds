/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Cria uma instância de {@link RNDS} devidamente configurada para
 * submissão de requisições à RNDS em nome de um estabelecimento de saúde.
 *
 * <p>Implementação do padrão <i>builder</i> para construção de instância
 * da classe {@link RNDS}.</p>
 */
public class RNDSBuilder {
    private String auth;
    private String ehr;
    private String keystore;
    private char[] password;
    private String requisitante;
    private RNDS.Estado estado = null;

    /**
     * Cria instância de {@link RNDS} para acesso ao ambiente de homologação
     * da RNDS.
     *
     * @param keystore     Caminho para o arquivo contendo o certificado
     *                     digital.
     * @param password     Senha para acesso ao certificado digital.
     * @param requisitante CNS do profissional de saúde, lotado no
     *                     estabelecimento de saúde em questão (aquele para o
     *                     qual é fornecido o certificado digital) e em nome
     *                     do qual requisições serão feitas. Este
     *                     requisitante só será empregado para cada
     *                     requisição que não indicar especificamente em nome
     *                     de quem a requisição é realizada.
     * @param estado       Estado no qual está localizado o estabelecimento
     *                     de saúde.
     * @return Instância apta para a interação com a RNDS.
     */
    public static RNDS homologacao(String keystore, char[] password,
                                   String requisitante, RNDS.Estado estado) {
        final String auth = "ehr-auth-hmg.saude.gov.br";
        final String ehr = "ehr-services.hmg.saude.gov.br";
        return new RNDS(auth, ehr, keystore, password, requisitante, estado);
    }

    /**
     * Cria instância de {@link RNDS} para acesso ao ambiente de produção da
     * RNDS.
     *
     * @param keystore     Caminho para o arquivo contendo o certificado
     *                     digital.
     * @param password     Senha para acesso ao certificado digital.
     * @param requisitante CNS do profissional de saúde, lotado no
     *                     estabelecimento de saúde em questão (aquele
     *                     para o
     *                     qual é fornecido o certificado digital) e
     *                     em nome
     *                     do qual requisições serão feitas. Este
     *                     requisitante só será empregado para cada
     *                     requisição que não indicar especificamente
     *                     em nome
     *                     de quem a requisição é realizada.
     * @param estado       Estado no qual está localizado o
     *                     estabelecimento
     *                     de saúde.
     * @return Instância apta a ser utilizada para interação com a RNDS.
     */
    public static RNDS producao(String keystore, char[] password,
                                String requisitante, RNDS.Estado estado) {
        final String auth = "ehr-auth.saude.gov.br";
        final String ehr = estado.toString() + "-ehr-services.saude.gov.br";
        return new RNDS(auth, ehr, keystore, password, requisitante, estado);
    }

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
     * Define o <i>host</i> dos serviços (<i>web services</i>) de saúde da RNDS.
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
        try {
            new FileInputStream(keystore);
        } catch (FileNotFoundException e) {
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

    /**
     * Cria uma instância de {@link RNDS}.
     *
     * @return A instância de {@link RNDS} em conformidade com os valores
     * definidos.
     */
    public RNDS build() {
        return new RNDS(auth, ehr, keystore, password, requisitante, estado);
    }
}