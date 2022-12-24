package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.NpmPackageValidationSupport;

import java.io.IOException;

@Slf4j
public class DefaultProfileVocabulary {

    public static final String URL = "http://www.saude.gov.br/fhir/r4/CodeSystem/BRCID10";

    public static void main(String[] args) throws IOException {
        FhirContext ctx = FhirContext.forR4();

        DefaultProfileValidationSupport defaultProfile =
                new DefaultProfileValidationSupport(ctx);

        defaultProfile.fetchValueSet("http://www.saude.gov.br/fhir/r4/ValueSet/BRCID10-ValueSet");

        defaultProfile.
    }
}
