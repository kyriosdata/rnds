package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerificarAssinatura {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Uso: java VerifySignature <arquivo-conteudo> <arquivo-assinatura> <arquivo-chave-publica>");
            return;
        }

        String contentFile = args[0];
        String signatureFile = args[1];
        String publicKeyFile = args[2];

        try {
            // Ler o conteúdo do arquivo
            byte[] contentData = readFile(contentFile);

            // Ler a assinatura do arquivo
            byte[] signatureData = readFile(signatureFile);
            byte[] assinatura = Base64.getDecoder().decode(signatureData);

            // Ler a chave pública do arquivo
            FileInputStream fin = new FileInputStream(publicKeyFile);
            CertificateFactory f = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
            PublicKey pk = certificate.getPublicKey();


            // Inicializar o objeto Signature com a chave pública
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pk);

            // Verificar a assinatura
            signature.update(contentData);
            boolean verified = signature.verify(assinatura);

            if (verified) {
                System.out.println("Assinatura verificada com sucesso. O conteúdo é autêntico.");
            } else {
                System.out.println("Falha na verificação da assinatura. O conteúdo pode ter sido modificado.");
            }
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

