package com.github.kyriosdata.rnds;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class Serializacao {

    public static void main(String[] args) {

        // Cria um recurso FHIR (Patient)

        System.out.println(getJsonPatient());
    }

    public static String getJsonPatient() {
        Patient paciente = getPatient();

        FhirContext ctx = FhirContext.forR4();

        // Para XML use ctx.newXmlParser()
        IParser parser = ctx.newJsonParser().setPrettyPrint(true);
        return parser.encodeResourceToString(paciente);
    }

    @NotNull
    public static Patient getPatient() {
        Patient paciente = new Patient();
        paciente.addName().setFamily("da Silva").addGiven("João");
        return paciente;
    }

    public static IBase fromJson(String arquivo) {
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newJsonParser();

        InputStream is = Serializacao.class.getClassLoader().getResourceAsStream(arquivo);
        return parser.parseResource(Patient.class, is);
    }

    public static IBase codeSystemFromXml(String arquivo) {
        FhirContext ctx = FhirContext.forR4();
        IParser parser = ctx.newXmlParser();

        InputStream is = Serializacao.class.getClassLoader().getResourceAsStream(arquivo);
        return parser.parseResource(CodeSystem.class, is);
    }
}
