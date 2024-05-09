package com.github.kyriosdata.rnds;

import java.io.FileInputStream;
import java.security.Signature;

public class VerificarIntegridadeConteudo {

    public static void main(String[] args) {
        String arquivoOriginal = "arquivo_original.txt";
        String arquivoAssinatura = "assinatura.txt";

        if (verificarAssinatura(arquivoOriginal, arquivoAssinatura)) {
            System.out.println("Assinatura válida! O conteúdo não foi alterado.");
        } else {
            System.out.println("Assinatura inválida! O conteúdo foi alterado.");
        }
    }

    public static boolean verificarAssinatura(String arquivoOriginal, String arquivoAssinatura) {
        try {
            // Carregar a assinatura
            FileInputStream assinaturaStream = new FileInputStream(arquivoAssinatura);
            byte[] assinatura = new byte[assinaturaStream.available()];
            assinaturaStream.read(assinatura);
            assinaturaStream.close();

            // Inicializar objeto de verificação de assinatura
            Signature verificadorAssinatura = Signature.getInstance("SHA256withRSA");

            // Carregar o arquivo original
            FileInputStream originalStream = new FileInputStream(arquivoOriginal);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = originalStream.read(buffer)) != -1) {
                verificadorAssinatura.update(buffer, 0, len);
            }
            originalStream.close();

            // Verificar a assinatura
            return verificadorAssinatura.verify(assinatura);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

