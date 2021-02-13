package com.github.kyriosdata.rnds;

import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Serializacao {

    public static void main(String[] args) {

        // Cria um recurso FHIR (Patient)
        Patient paciente = new Patient();
        paciente.addName().setFamily("da Silva").addGiven("João");

        FhirContext ctx = FhirContext.forR4();

        // Para XML use ctx.newXmlParser()
        IParser parser = ctx.newJsonParser().setPrettyPrint(true);
        String json = parser.encodeResourceToString(paciente);

        System.out.println(json);
    }
}
