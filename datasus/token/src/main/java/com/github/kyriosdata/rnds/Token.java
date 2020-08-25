/*
 * Copyright (c) 2020.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.rnds;

import java.util.Objects;

public class Token {

    /**
     * Endereço do qual o token será obtido.
     */
    private static final String SERVER =
            "https://ehr-auth-hmg.saude.gov.br/api/token";

    /**
     * Obtém path completo do nome do arquivo fornecido que se encontra
     * no diretório resources.
     *
     * @param arquivo Nome do arquivo contido no diretório "resources".
     * @return O caminho completo para o arquivo cujo nome é fornecido.
     */
    private static String fromResource(final String arquivo) {
        Class<Token> appClass = Token.class;
        return appClass.getClassLoader().getResource(arquivo).getPath();
    }

    /**
     * Aplicação que recupera <i>token</i> de acesso aos serviços da RNDS.
     * @param args Nenhum argumento é esperado via linha de comandos.
     */
    public static void main(String[] args) {
        final String arquivo = System.getenv("RNDS_CERT_FILE");
        Objects.requireNonNull(arquivo, "RNDS_CERT_FILE não definida");

        final String server = System.getenv("RNDS_AUTH_SERVER");
        Objects.requireNonNull(server, "RNDS_AUTH_SERVER não definida");

        String senha = System.getenv("RNDS_CERT_SENHA");
        Objects.requireNonNull(senha, "RNDS_CERT_SENHA não definida");
        final char[] password = senha.toCharArray();

        final String token = RNDS.getToken(SERVER, arquivo, password);
        System.out.println(token);
    }
}
