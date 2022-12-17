package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.StringType;

public class Validacao {

    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();

        // Create a validation support chain
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
                new DefaultProfileValidationSupport(ctx),
                new InMemoryTerminologyServerValidationSupport(ctx),
                new CommonCodeSystemsTerminologyService(ctx)
        );

        // Create a FhirInstanceValidator and register it to a validator
        FhirValidator validator = ctx.newValidator();
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(validationSupportChain);
        validator.registerValidatorModule(instanceValidator);

        /*
         * If you want, you can configure settings on the validator to adjust
         * its behaviour during validation
         */
        instanceValidator.setAnyExtensionsAllowed(true);

        /*
         * Let's create a resource to validate. This Observation has some fields
         * populated, but it is missing Observation.status, which is mandatory.
         */
        Observation obs = new Observation();
        obs.getCode().addCoding().setSystem("http://loinc.org").setCode("12345-6");
        obs.setValue(new StringType("This is a value"));
        obs.setStatus(Observation.ObservationStatus.FINAL);

        // Validate
        ValidationResult result = validator.validateWithResult(obs);

        /*
         * Note: You can also explicitly declare a profile to validate against
         * using the block below.
         */
// ValidationResult result = validator.validateWithResult(obs, new ValidationOptions().addProfile("http://myprofile.com"));

// Do we have any errors or fatal errors?
        System.out.println(result.isSuccessful()); // false

// Show the issues
        for (SingleValidationMessage next : result.getMessages()) {
            System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
        }
// Prints:
// Next issue ERROR - /f:Observation - Element '/f:Observation.status': minimum required = 1, but only found 0
// Next issue WARNING - /f:Observation/f:code - Unable to validate code "12345-6" in code system "http://loinc.org"

// You can also convert the result into an operation outcome if you
// need to return one from a server
        OperationOutcome oo = (OperationOutcome) result.toOperationOutcome();

        IParser parser = ctx.newJsonParser();
        parser.setPrettyPrint(true);
        System.out.println(parser.encodeResourceToString(oo));
    }
}
