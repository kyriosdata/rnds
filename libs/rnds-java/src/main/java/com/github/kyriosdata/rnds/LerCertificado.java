package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class LerCertificado {

    public static void main(String[] args) {
        String archivo = "/tmp/publico.cer";

        try {
            // Cargar el certificado desde el archivo
            FileInputStream fis = new FileInputStream(archivo);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificado = (X509Certificate) cf.generateCertificate(fis);
            fis.close();

            // Mostrar información del certificado
            System.out.println("Versión: " + certificado.getVersion());
            System.out.println("Sujeto: " + certificado.getSubjectDN());
            System.out.println("Emisor: " + certificado.getIssuerDN());
            System.out.println("Número de serie: " + certificado.getSerialNumber());
            System.out.println("Válido desde: " + certificado.getNotBefore());
            System.out.println("Válido hasta: " + certificado.getNotAfter());
            System.out.println("Algoritmo de firma: " + certificado.getSigAlgName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
