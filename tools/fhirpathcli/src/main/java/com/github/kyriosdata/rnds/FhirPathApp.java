package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FhirPathApp {
    public static void main(String[] args) throws IOException {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);

        IParser json = ctx.newJsonParser();
        json.setPrettyPrint(true);

        String recursoStr = Files.readString(Paths.get(args[0]));
        IBase resource = json.parseResource(recursoStr);

        log.info("Iniciando avaliação de FHIRPath...");

        List<Base> resposta = fp.evaluate(resource, args[1], Base.class);

        log.info("Avaliação de FHIRPath concluída.");

        for (Base base : resposta) {
            if (base instanceof IBaseResource) {
                System.out.printf("%s\n", json.encodeResourceToString((IBaseResource) base));
                continue;
            }

            System.out.printf("%s\n", base.toString());
        }

        log.info("Aplicação encerrada.");
    }
}
