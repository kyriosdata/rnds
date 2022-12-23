package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.RemoteTerminologyServiceValidationSupport;
import org.hl7.fhir.r4.model.ValueSet;

@Slf4j
public class ServidorLoinc {

    public static final String LOINC_ID = "http://loinc.org";
    public static final String LOINC_SERVER = "https://fhir.loinc.org/";

    public static void main(String[] args) {
        String user = System.getenv("LOINC_USERNAME");
        String pass = System.getenv("LOINC_PASSWORD");
        if (user == null || pass == null) {
            log.error("Variáveis LOINC_USERNAME e LOINC_PASSWORD indefinidas");
            System.exit(1);
        }

        if (args.length < 1) {
            log.error("Não fornecido código LOINC para consulta.");
            System.exit(1);
        }

        FhirContext ctx = FhirContext.forR4();
        RemoteTerminologyServiceValidationSupport remote =
                new RemoteTerminologyServiceValidationSupport(ctx);
        remote.addClientInterceptor(new BasicAuthInterceptor(user, pass));
        remote.setBaseUrl(LOINC_SERVER);

        ValueSet cs = (ValueSet) remote.fetchValueSet("http://loinc.org/vs/LL836-8");
        IParser parser = ctx.newJsonParser();
        parser.setPrettyPrint(true);
        System.out.println(parser.encodeResourceToString(cs));

        try {
            IValidationSupport.CodeValidationResult r = remote.validateCode(
                    null,
                    null,
                    LOINC_ID,
                    args[0],
                    null,
                    null);

            String format = "Código %s pertence ao sistema %s? %s";
            String str = String.format(format, args[0], LOINC_ID, r.isOk());
            System.out.println(str);
        } catch (AuthenticationException exp) {
            log.error("Erro de autenticação", exp);
            System.out.println("Erro de autenticação");
        }
    }
}