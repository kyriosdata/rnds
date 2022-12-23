package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.CodeSystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Cid10 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        CodeSystem cs = getCodeSystem();
        System.out.println(cs.getUrl());
    }

    public static CodeSystem getCodeSystem() throws IOException {
        String arquivo = "BRCID10.xml";
        URL url = Cid10.class.getClassLoader().getResource(arquivo);

        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newXmlParser();
        parser.setPrettyPrint(true);
        CodeSystem cs = (CodeSystem) parser.parseResource(url.openStream());
        return cs;
    }

}
