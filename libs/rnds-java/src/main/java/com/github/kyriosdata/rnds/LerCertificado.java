package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class LerCertificado {

    public static void main(String[] args) {
        String arquivo = "/rnds-chave-publica-producao.pem";

        try {
            // Cargar el certificado desde el archivo
            InputStream is = LerCertificado.class.getResourceAsStream(arquivo);
            //FileInputStream fis = new FileInputStream(is);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificado = (X509Certificate) cf.generateCertificate(is);
            is.close();

            // Mostrar información del certificado
            System.out.println("Version: " + certificado.getVersion());
            System.out.println("Sujeito: " + certificado.getSubjectDN());
            System.out.println("Emissor: " + certificado.getIssuerDN());
            System.out.println("Número de série: " + certificado.getSerialNumber());
            System.out.println("Válido desde: " + certificado.getNotBefore());
            System.out.println("Válido até: " + certificado.getNotAfter());
            System.out.println("Algoritmo de assinatura: " + certificado.getSigAlgName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
