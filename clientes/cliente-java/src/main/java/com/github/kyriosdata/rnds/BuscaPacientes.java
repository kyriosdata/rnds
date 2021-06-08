package com.github.kyriosdata.rnds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class BuscaPacientes {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(BuscaPacientes.class);

        FhirContext ctx = FhirContext.forR4();
        final String SERVER = "http://hapi.fhir.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(SERVER);
        Bundle resposta = client.search().forResource(Patient.class).returnBundle(Bundle.class).execute();
        final String peloMenosUm = resposta.getEntry().isEmpty() ? "NÃO" : "SIM";

        logger.info("Acessando servidor {}", SERVER);
        logger.info("Pelo menos um paciente? {}", peloMenosUm);
    }
}
