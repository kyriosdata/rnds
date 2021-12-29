/**
 * Fábrica de Software - Instituto de Informática (UFG)
 * Fábio Nogueira de Lucena
 */
package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IElement;
import ca.uhn.fhir.parser.IParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Patient;

import java.io.InputStream;
import java.util.List;

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

    public static void main(String[] args) {
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

    /**
     * Serializa recurso em JSON.
     *
     * @param resource Recurso a ser serializado.
     *
     * @return Sequência de caracteres JSON correspondente ao recurso
     * fornecido.
     *
     * @see #fromJson(InputStream)
     */
    public String toJson(IBaseResource resource) {
        return json.encodeResourceToString(resource);
    }

    /**
     * Obtém recurso a partir do stream fornecido.
     *
     * @param is O stream a partir do qual o recurso será obtido.
     *
     * @return Recurso recuperado a partir do stream.
     *
     * @see #toJson(IBaseResource)
     */
    public IBaseResource fromJson(InputStream is) {
        return json.parseResource(is);
    }

    public List<Base> avaliar(IBaseResource resource, String sentenca) {
        return fpr4.evaluate(resource, sentenca, Base.class);
    }

    public String toJson(IElement datatype) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(datatype);
    }

}
