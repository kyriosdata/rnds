package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class ForcaConexao {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        final String SERVER = "http://hapi.fhir.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(SERVER);
        client.forceConformanceCheck();
    }
}
