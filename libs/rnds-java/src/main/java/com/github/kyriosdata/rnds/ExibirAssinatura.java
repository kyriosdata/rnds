package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Signature;
import java.security.cert.Certificate;

public class ExibirAssinatura {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java DisplaySignatureInfo <arquivo-assinatura>");
            return;
        }

        String signatureFile = args[0];

        try {
            // Carregar a assinatura do arquivo
            byte[] signatureBytes = readFile(signatureFile);

            // Criar um objeto Signature e inicializá-lo com uma instância de Signature
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify((Certificate) null);

            // Carregar a assinatura no objeto Signature
            signature.update(signatureBytes);

            // Exibir informações sobre a assinatura
            System.out.println("Algoritmo de assinatura: " + signature.getAlgorithm());
            System.out.println("Estado da verificação: " + signature.verify(signatureBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        return data;
    }
}

