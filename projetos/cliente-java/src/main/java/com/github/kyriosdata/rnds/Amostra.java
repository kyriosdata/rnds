package com.github.kyriosdata.rnds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Specimen;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Amostra {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Amostra.class);

        FhirContext ctx = FhirContext.forR4();
        IParser jsonParser = ctx.newJsonParser();
        jsonParser.setPrettyPrint(true);

        // Monta instância de Specimen

        Coding coding = new Coding();
        coding.setSystem("http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoAmostraGAL");
        coding.setCode("SGHEM");

        CodeableConcept cc = new CodeableConcept();
        cc.addCoding(coding);

        Specimen amostra = new Specimen();
        amostra.setType(cc);

        // Produz representação JSON da instância criada
        String json = jsonParser.encodeResourceToString(amostra);
        logger.info("Representação JSON: {}", json);
    }
}
