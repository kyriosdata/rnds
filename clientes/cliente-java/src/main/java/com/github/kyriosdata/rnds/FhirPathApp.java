package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class FhirPathApp {
    public static void main(String[] args) {
        FhirPathR4 fp = new FhirPathR4(FhirContext.forR4());
        fp.parse("Patient.name");

        Patient patient = Serializacao.getPatient();
        List<HumanName> evaluate = fp.evaluate(patient, "Patient.name", HumanName.class);
        System.out.println(evaluate.get(0).getNameAsSingleString());
    }
}
