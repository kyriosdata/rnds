package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.StrictErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
public class ToJson {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        ctx.setParserErrorHandler(new StrictErrorHandler());

        if (args.length < 1) {
            log.error("Não fornecido arquivo xml contendo recurso FHIR");
            System.exit(1);
        }

        InputStream fis = null;
        try {
            File arquivo = new File(args[0]);
            fis = new FileInputStream(arquivo);
        } catch (FileNotFoundException e) {
            log.error("Erro com arquivo: {}", args[0]);
        }

        IParser input = ctx.newXmlParser();
        IBaseResource recurso = input.parseResource(fis);

        IParser output = ctx.newJsonParser();
        output.setPrettyPrint(true);

        // Produz representação JSON da instância criada
        String json = output.encodeResourceToString(recurso);
        System.out.println(json);
    }
}
