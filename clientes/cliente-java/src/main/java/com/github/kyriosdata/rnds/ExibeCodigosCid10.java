package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.CodeSystem;

public class ExibeCodigosCid10 {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);

        CodeSystem codeSystem = (CodeSystem) Serializacao.codeSystemFromXml("BRCID10.xml");
        System.out.println(String.format("Tipo: %s", codeSystem.fhirType()));

        codeSystem.getConcept().stream()
                .forEach(c -> System.out.println(c.getCode() + " " + c.getDisplay()));
    }
}
