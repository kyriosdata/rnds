package com.github.kyriosdata.rnds;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Base;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FhirPathLibTest {

    @Test
    void recuperaPropriedadeName() {
        InputStream is = FhirPathLib.class
                .getClassLoader().getResourceAsStream("patient.json");
        assertNotNull(is);

        FhirPathLib fpl = new FhirPathLib();
        IBaseResource resource = fpl.fromJson(is);
        assertFalse(resource.isEmpty());

        List<Base> resposta = fpl.avaliar(resource, "Patient.name");
        assertEquals(3, resposta.size());
    }
}
