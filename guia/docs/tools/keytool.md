---
id: keytool
title: keytool
sidebar_label: keytool
---

[keytool](https://docs.oracle.com/javase/10/tools/keytool.htm#JSWOR-GUID-5990A2E4-78E3-47B7-AE75-6D1826259549) é uma ferramenta usada via linha de comandos para gerenciar um
_keystore_, ou base de dados contendo chaves, cadeias de certificados e certificados confiáveis (_trusted certificates_). A _keytool_ é uma das
várias ferramentas que acompanham as distribuições de Java, por exemplo,
[OpenJDK](https://adoptopenjdk.net/).

A ferramenta _keytool_ é empregada para acrescentar o certificado da autoridade
certificadora que criou o certificado empregado pelos serviços oferecidos pelo
DATASUS. Desta forma, uma aplicação em Java terá "confiança" no certificado
empregado pelos servidores do DATASUS. Veja [autenticação](../autenticacao.md) para detalhes.
