package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FhirPathApp {
    public static void main(String[] args) throws IOException {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);
        IParser json = ctx.newJsonParser();

        // Pass 'medicationRequestUSCORE.json' file as arg
        InputStream fis = Files.newInputStream(Paths.get(args[0]));

        IBase resource = json.parseResource(fis);

        String regex = "\\\\b(?<year>\\\\d{4})-(?<month>\\\\d{2})-(?<day>\\\\d{2})\\\\b";
        String substitution = "${day}/${month}/${year}";
        String expression = String.format("authoredOn.replaceMatches('%s', '%s')", regex, substitution);

        // Print exp without so many backslashes (as defined by FHIRPath)
        System.out.println(expression);

        List<Base> resposta = fp.evaluate(resource, expression, Base.class);

        System.out.println(resposta.get(0));
    }
}
