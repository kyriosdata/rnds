package com.github.kyriosdata.rnds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.AdditionalRequestHeadersInterceptor;

public class Autorizacao {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Autorizacao.class);

        FhirContext ctx = FhirContext.forR4();
        final String SERVER = "http://hapi.fhir.org/baseR4";
        IGenericClient client = ctx.newRestfulGenericClient(SERVER);

        // AUTORIZAÇÃO NECESSÁRIA PARA ACESSO À RNDS
        // Informações são necessárias: (a) token e (b) CNS do requisitante
        // (valores inválidos pois SERVER não exige autenticação)
        final String token = "recuperado de requisição especifica";
        final String authorization = "CNS do requisitante";

        // DOIS HEADER SÃO USADOS PARA TRANSMITIR TAIS INFORMAÇÕES
        // "X-Authorization-Server" e "Authorization"
        AdditionalRequestHeadersInterceptor interceptor = new AdditionalRequestHeadersInterceptor();

        // X-Authorization-Server
        // Valor definido pela concatenação de "Bearer " com token
        interceptor.addHeaderValue("X-Authorization-Server", "Bearer " + token);

        // Authorization (CNS do profissional requisitante)
        interceptor.addHeaderValue("Authorization", authorization);

        // Registra os headers com o cliente
        client.registerInterceptor(interceptor);

        // CUIDADO: apenas para aprendizado...
        logger.info("X-Authorization-Server: Bearer {}", token);
        logger.info("Authorization: {}", authorization);

        // Nada mais se altera
        // FIM DO ACRÉSCIMO DE SEGURANÇA

        Bundle resposta = client.search().forResource(Patient.class).returnBundle(Bundle.class).execute();
        final String peloMenosUm = resposta.getEntry().isEmpty() ? "NÃO" : "SIM";

        logger.info("Acessando servidor {}", SERVER);
        logger.info("Pelo menos um paciente? {}", peloMenosUm);
    }
}
