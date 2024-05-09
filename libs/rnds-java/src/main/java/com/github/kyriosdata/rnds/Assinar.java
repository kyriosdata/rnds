package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class Assinar {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java SignWithICPBrasilCert <arquivo.pfx> <arquivo-entrada> <arquivo-saida>");
            return;
        }

        String pfxFile = args[0];
        String inputFile = args[1];
        String outputFile = args[2];

        try {
            // Carregar o arquivo .pfx
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(pfxFile), "senha".toCharArray()); // Substitua "senha" pela senha do arquivo pfx

            // Obter a chave privada do certificado ICP-Brasil
            String alias = keystore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, "senha".toCharArray()); // Substitua "senha" pela senha do arquivo pfx

            // Inicializar o objeto Signature com a chave privada
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            // Ler o conteúdo do arquivo de entrada
            byte[] data = readFile(inputFile);

            // Assinar os dados
            signature.update(data);
            byte[] signedData = signature.sign();

            // Codificar a assinatura para Base64
            String base64Signature = Base64.getEncoder().encodeToString(signedData);

            // Escrever a assinatura no arquivo de saída
            writeFile(outputFile, base64Signature.getBytes());

            System.out.println("Assinatura digital gerada com sucesso.");
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

    private static void writeFile(String filePath, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(data);
        fos.close();
    }
}

