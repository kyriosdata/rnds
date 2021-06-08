/*
 * Copyright (c) 2021.
 * Fábio Nogueira de Lucena - Instituto de Informática (UFG)
 * Apache-2.0 License - http://www.apache.org/licenses/LICENSE-2.0
 */

package com.github.kyriosdata.rnds;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ReadFromResources {

    @Test
    void carregaResourceJson() throws IOException, URISyntaxException {
        Path json = fromResource("14.json");
        List<String> linhas = Files.readAllLines(json);
        assertFalse(linhas.isEmpty());
    }

    /**
     * Obtém path completo do nome do arquivo fornecido que se encontra no diretório
     * resources.
     *
     * @param arquivo Nome do arquivo contido no diretório "resources".
     * @return O caminho completo para o arquivo cujo nome é fornecido.
     * @throws URISyntaxException
     */
    static Path fromResource(final String arquivo) throws URISyntaxException {
        Class<ReadFromResources> appClass = ReadFromResources.class;
        URL url = appClass.getClassLoader().getResource(arquivo);
        URI uri = url.toURI();
        return Paths.get(uri);
    }
}
