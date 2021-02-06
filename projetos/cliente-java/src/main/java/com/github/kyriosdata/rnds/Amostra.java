package com.github.kyriosdata.rnds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Meta;
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

        Meta meta = new Meta();
        CanonicalType ct = new CanonicalType();
        ct.setValue("http://www.saude.gov.br/fhir/r4/StructureDefinition/BRAmostraBiologica-1.0");
        List<CanonicalType> perfis = new ArrayList<>();
        perfis.add(ct);
        meta.setProfile(perfis);

        amostra.setMeta(meta);

        // Produz representação JSON da instância criada
        String json = jsonParser.encodeResourceToString(amostra);
        logger.info("Representação JSON: {}", json);
    }
}
