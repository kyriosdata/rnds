package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.ConceptValidationOptions;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.context.support.IValidationSupport;
import ca.uhn.fhir.context.support.ValidationSupportContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.RemoteTerminologyServiceValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.StringType;

@Slf4j
public class Ucum {

    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();

        CommonCodeSystemsTerminologyService ucum =
                new CommonCodeSystemsTerminologyService(ctx);

        ValidationSupportContext vsc = new ValidationSupportContext(ucum);

        System.out.println(ucum.isValueSetSupported(vsc, "http://hl7.org/fhir/ValueSet/mimetypes"));

        // CodeSystem/$lookup (mime types)
        IValidationSupport.LookupCodeResult resultado =
                ucum.lookupCode(null, "urn:ietf:bcp:13", "text/plain");
        System.out.println(resultado.isFound());

        // CodeSystem/$lookup (ucum)
        resultado =
                ucum.lookupCode(null, "http://unitsofmeasure.org", "x");
        System.out.println("x " + resultado.isFound());

        // ValueSet/$validate-code (ucum)
        IValidationSupport.CodeValidationResult x = ucum.validateCode(null, null, "http://unitsofmeasure.org", "mm", null, "http://hl7.org/fhir/ValueSet/ucum-units");
        System.out.println("mm " + x.isOk());
    }
}
