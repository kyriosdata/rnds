package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.ParametersUtil;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class FhirPathApp {
    public static void main(String[] args) throws IOException {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);

        IParser json = ctx.newJsonParser();
        json.setPrettyPrint(true);

        String recursoStr = Files.readString(Paths.get(args[0]));
        IBase resource = json.parseResource(recursoStr);

        List<IBase> resposta = fp.evaluate(resource, args[1], IBase.class);


        /*
        for (IBase base : resposta) {
            if (base instanceof IBaseResource) {
                System.out.printf("%s\n", json.encodeResourceToString((IBaseResource) base));
                continue;
            }

            System.out.printf("%s\n", base.toString());
        }
        */

        IBaseParameters responseParameters = ParametersUtil.newInstance(ctx);

        IBase resultPart = ParametersUtil.addParameterToParameters(ctx, responseParameters, "result");

        for (IBase nextOutput : resposta) {
            if (nextOutput instanceof IBaseResource) {
                ParametersUtil.addPartResource(ctx, resultPart, "result", (IBaseResource) nextOutput);
            } else {
                ParametersUtil.addPart(ctx, resultPart, "result", nextOutput);
            }
        }

        System.out.printf("%s\n", json.encodeResourceToString(responseParameters));
    }
}
