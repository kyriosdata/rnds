package com.github.kyriosdata.rnds;

import org.hl7.fhir.r4.model.Base64BinaryType;

public class Tipos {
    public static void main(String[] args) {
        Base64BinaryType bin = new Base64BinaryType("teste");
        System.out.println(bin.getValueAsString());
    }
}
