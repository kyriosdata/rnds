package com.github.kyriosdata.rnds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;

public class ForcaConexao {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ForcaConexao.class);

        FhirContext ctx = FhirContext.forR4();
        final String SERVER = "http://hapi.fhir.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(SERVER);
        try {
            client.forceConformanceCheck();
            logger.info("Conexão realizada satisfatoriamente ({})!", SERVER);
        } catch (FhirClientConnectionException cnxExp) {
            logger.error("Erro ao tentar acesso ao {}", SERVER);
        }
    }
}
