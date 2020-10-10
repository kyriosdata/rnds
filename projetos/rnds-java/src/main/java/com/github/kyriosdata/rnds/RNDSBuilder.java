/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

/**
 * Cria uma instância de {@link RNDS} devidamente configurada para
 * submissão de requisições à RNDS em nome de um estabelecimento de saúde.
 *
 * <p>Variáveis de ambiente serão empregadas, caso valor específico não seja
 * estabelecido por meio de método. Por exemplo, se o requisitante não é
 * definido, então o valor correspondente será buscado de variável de
 * ambiente correspondente. As variáveis são definidas abaixo:
 * </p>
 *
 * <table>
 *     <tr>
 *         <th>Varável</th>
 *         <th>Descrição</th>
 *     </tr>
 *     <tr>
 *         <td><b>RNDS_AUTH</b></td>
 *         <td>Endereço do serviço de autenticação.</td>
 *     </tr>
 *     <tr>
 *         <td><b>RNDS_EHR</b></td>
 *         <td>Endereço dos serviços (<i>web services</i>) de saúde.</td>
 *     </tr>
 *     <tr>
 *         <td><b>RNDS_CERTIFICADO_ENDERECO</b></td>
 *         <td>Endereço <i>web</i> (por exemplo, iniciado por <i>http</i>)
 *         ou caminho do arquivo contendo o certificado digital.</td>
 *     </tr>
 *     <tr>
 *         <td><b>RNDS_CERTIFICADO_SENHA</b></td>
 *         <td>Senha de acesso ao conteúdo do certificado digital.</td>
 *     </tr>
 *     <tr>
 *         <td><b>RNDS_REQUISITANTE_CNS</b></td>
 *         <td>CNS do profissional de saúde em nome do qual requisições
 *         serão submetidas, se não informado o contrário por requisição.</td>
 *     </tr>
 * </table>
 *
 * <p>Implementação do padrão <i>builder</i> para construção de instância
 * da classe {@link RNDS}.</p>
 */
public class RNDSBuilder {

    public static final String AUTH = "ehr-auth.saude.gov.br";
    public static final String EHR_SUFFIX = "-ehr-services.saude.gov.br";
    public static final String AUTH_HMG = "ehr-auth-hmg.saude.gov.br";
    public static final String EHR_HMG = "ehr-services.hmg.saude.gov.br";

    public static final String RNDS_AUTH = "RNDS_AUTH";
    public static final String RNDS_EHR = "RNDS_EHR";
    public static final String RNDS_CERTIFICADO_ENDERECO =
            "RNDS_CERTIFICADO_ENDERECO";
    public static final String RNDS_CERTIFICADO_SENHA =
            "RNDS_CERTIFICADO_SENHA";
    public static final String RNDS_REQUISITANTE_CNS =
            "RNDS_REQUISITANTE_CNS";
    public static final String RNDS_REQUISITANTE_UF =
            "RNDS_REQUISITANTE_UF";

    private String auth;
    private String ehr;
    private String keystore;
    private char[] password;
    private String requisitante;
    private RNDS.Estado estado = null;

    /**
     * Cria instância do <i>builder</i> usando valores obtidos de variáveis
     * de ambiente, caso definidas.
     */
    public RNDSBuilder() {
        auth(System.getenv(RNDS_AUTH));
        ehr(System.getenv(RNDS_EHR));
        keystore(System.getenv(RNDS_CERTIFICADO_ENDERECO));
        password(System.getenv(RNDS_CERTIFICADO_SENHA).toCharArray());
        requisitante(System.getenv(RNDS_REQUISITANTE_CNS));
        estado(System.getenv(RNDS_REQUISITANTE_UF));
    }

    /**
     * Cria instância de {@link RNDS} para acesso ao ambiente de homologação
     * da RNDS. Os endereços padrão para os serviços de autenticação e saúde,
     * caso não tenham sido fornecidos, serão empregados.
     *
     * @return Instância apta para a interação com a RNDS.
     */
    public RNDS homologacao() {
        if (auth == null) {
            auth(AUTH_HMG);
        }

        if (ehr == null) {
            ehr(EHR_HMG);
        }

        return build();
    }

    /**
     * Cria instância de {@link RNDS} para acesso ao ambiente de produção da
     * RNDS.
     *
     * @return Instância apta a ser utilizada para interação com a RNDS.
     */
    public RNDS producao() {

        if (auth == null) {
            auth(AUTH);
        }

        if (ehr == null) {
            if (estado == null) {
                throw new NullPointerException("estado não definido");
            }

            ehr(estado.toString() + EHR_SUFFIX);
        }

        return build();
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
    public RNDSBuilder estado(String estado) {
        try {
            this.estado = RNDS.Estado.valueOf(estado);
        } catch (Exception exception) {
            this.estado = null;
        }
        
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