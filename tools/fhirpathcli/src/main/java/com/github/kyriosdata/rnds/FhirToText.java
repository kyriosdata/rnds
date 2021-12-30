package com.github.kyriosdata.rnds;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Period;

import java.util.List;

/**
 * Recursos e elementos FHIR são entidades recursivas.
 * Simplesmente exibir o conteúdo no formato texto
 * pode resultar em "muito" texto. Por exemplo,
 * Patient pode possuir links para outros resources
 * que se referem à mesma pessoa, talvez vários
 * Patient, neste caso, teríamos até um "laço".
 */
public class FhirToText {

    public static void main(String[] args) {
        System.out.println(toString(new Period()));

        Patient paciente = new Patient();
        paciente.addName().setFamily("da Silva").addGiven("João");

        System.out.println(toString(paciente));
    }

    public static String toString(IBase elemento) {
        StringBuilder sb = new StringBuilder();
        String fhirType = elemento.fhirType();
        switch (fhirType) {
            case "Period":
                sb.append(fhirType);
                if (elemento.isEmpty()) {
                    sb.append(" vazio");
                }
                break;
            case "Patient":
                sb.append(fhirType);
                Patient paciente = (Patient) elemento;

                // Dado opcional (active)
                paciente.getActiveElement().isEmpty();

                List<Base> valores = paciente.getChildByName("active").getValues();
                System.out.println(valores.size());
                System.out.println(valores.get(0).isEmpty());
                break;
            default:
                sb.append(fhirType);
                sb.append(" serialização não definida");
        }

        return sb.toString();
    }
}
