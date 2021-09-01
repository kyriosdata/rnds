package com.github.kyriosdata.rnds;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class BuscaPacientes {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        final String SERVER = "http://hapi.fhir.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(SERVER);
        Bundle resposta = client.search().forResource(Patient.class).returnBundle(Bundle.class).execute();
        boolean peloMenosUm = !resposta.getEntry().isEmpty();
        System.out.println("Pelo menos um? " + peloMenosUm);
    }
}
