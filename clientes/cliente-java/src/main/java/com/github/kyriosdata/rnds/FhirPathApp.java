package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Property;

import java.util.List;

public class FhirPathApp {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);

        IBase patient = Serializacao.fromJson("patient.json");
        List<Base> evaluate = fp.evaluate(patient, "Patient.telecom.where(rank.hasValue())", Base.class);
        System.out.println(String.format("Resultados: %d", evaluate.size()));
        Base base = evaluate.get(0);
        for (Property property : base.children()) {
            System.out.println(property + " " + property.getValues());
        }

//        IParser parser = ctx.newJsonParser().setPrettyPrint(true);
//        String json = parser.encodeResourceToString(evaluate.get(0).castToResource(evaluate.get(0)));
//        System.out.println(json);
    }
}
