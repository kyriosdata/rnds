package com.github.kyriosdata.rnds;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.hapi.fluentpath.FhirPathR4;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Property;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.codesystems.BundleType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FhirPathApp {
    public static void main(String[] args) {
        FhirContext ctx = FhirContext.forR4();
        FhirPathR4 fp = new FhirPathR4(ctx);

        IBase patient = Serializacao.fromJson("patient.json");
        IBase outro = Serializacao.fromJson("patient-outro.json");

        // Montar um bundle
        Bundle bundle = new Bundle();
        Bundle.BundleEntryComponent entry1 = new Bundle.BundleEntryComponent();
        entry1.setResource((Resource)patient);
        Bundle.BundleEntryComponent entry2 = new Bundle.BundleEntryComponent();
        entry2.setResource((Resource)outro);

        bundle.addEntry(entry1);
        bundle.addEntry(entry2);

        String c1 = "Patient.telecom.where(rank.hasValue())";
        String c2 = "Patient.name.exists()";
        String c3 = "Patient.name";

        // FIXME quando bundle substitui patient um erro ocorre
        List<Base> resposta = fp.evaluate(patient, c3, Base.class);

        showTotal(resposta);
        showTipos(resposta);
        System.out.println(resposta.get(0).fhirType());
        System.out.println(String.format("isResource: %b", resposta.get(0).isResource()));
        System.out.println(String.format("isPrimitive: %b", resposta.get(0).isPrimitive()));
        Base base = resposta.get(0);
        for (Property property : base.children()) {
            System.out.println(property + " " + property.getValues());
        }

        // IParser parser = ctx.newJsonParser().setPrettyPrint(true);
        // String json =
        // parser.encodeResourceToString(resposta.get(0).castToResource(resposta.get(0)));
        // System.out.println(json);
    }

    public static void showTotal(List<Base> resposta) {
        System.out.println(String.format("Total de elementos: %d", resposta.size()));
    }

    public static void showTipos(List<Base> resposta) {
        List<String> tipos = resposta.stream()
                .map(i -> i.fhirType()).collect(Collectors.toList());
        System.out.println(String.format("Tipos: %s", linhaComIndice(tipos)));
    }

    public static String linha(List<String> resposta) {
        return String.join(" ", resposta);
    }

    public static String linhaComIndice(List<String> resposta) {
        int indice = 0;
        final List<String> itens = new ArrayList<>(resposta.size());
        for (String componente: resposta) {
            itens.add(String.format("[%d]%s", indice++, componente));
        }

        return linha(itens);
    }
}
