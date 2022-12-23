package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.NpmPackageValidationSupport;
import org.hl7.fhir.r4.model.CodeSystem;

import java.io.IOException;

@Slf4j
public class Npm {

    public static final String URL = "http://www.saude.gov.br/fhir/r4/CodeSystem/BRCID10";

    public static void main(String[] args) throws IOException {
        FhirContext ctx = FhirContext.forR4();

        NpmPackageValidationSupport npm =
                new NpmPackageValidationSupport(ctx);

        CodeSystem codeSystem = Cid10.getCodeSystem();
        npm.addCodeSystem(codeSystem);

        ValidationSupportContext con = new ValidationSupportContext(npm);
        InMemoryTerminologyServerValidationSupport ram = new InMemoryTerminologyServerValidationSupport(ctx);
        System.out.println(ram.isCodeSystemSupported(con, URL));
        IValidationSupport.LookupCodeResult a001 = ram.lookupCode(con, URL, "A91");
        System.out.println("A00 " + a001.isFound() + " " + a001.getCodeDisplay());
    }
}
