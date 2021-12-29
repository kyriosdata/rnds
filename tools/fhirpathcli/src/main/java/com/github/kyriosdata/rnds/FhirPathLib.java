/**
 * Fábrica de Software - Instituto de Informática (UFG)
 * Fábio Nogueira de Lucena
 */
package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IDatatype;
import ca.uhn.fhir.model.api.IElement;
import ca.uhn.fhir.parser.IParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.output.JsonStream;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class FhirPathLib {

    private IParser json;
    private IParser xml;
    private FhirPathR4 fpr4;

    public FhirPathLib() {
        FhirContext ctx = FhirContext.forR4();
        json = ctx.newJsonParser().setPrettyPrint(true);
        xml = ctx.newXmlParser().setPrettyPrint(true);
        fpr4 = new FhirPathR4(ctx);
    }

    public static void main(String[] args) throws JsonProcessingException {
        FhirPathLib fpl = new FhirPathLib();
        System.out.println(fpl.getJsonPatient());
    }

    public String getJsonPatient() {
        return toJson(getPatient());
    }

    public Patient getPatient() {
        Patient paciente = new Patient();
        paciente.addName().setFamily("da Silva").addGiven("João");
        return paciente;
    }

    public String toJson(IBaseResource resource) {
        return json.encodeResourceToString(resource);
    }

    public IBaseResource fromJson(InputStream is) {
        return json.parseResource(is);
    }

    public List<String> avaliar(IBaseResource resource, String sentenca) {
        List<Base> avaliado = fpr4.evaluate(resource, sentenca, Base.class);
        return avaliado.stream()
                .map(b -> toJson((IBaseResource) b)).collect(Collectors.toList());
    }

    public String toJson(IElement datatype) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(datatype);
    }

}
