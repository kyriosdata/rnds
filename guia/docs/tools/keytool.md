---
id: keytool
title: keytool
sidebar_label: keytool
---

_keytool_ é uma ferramenta usada via linha de comandos para gerenciar um
_keystore_, ou base de dados contendo chaves, cadeias de certificados e certificados confiáveis (_trusted certificates_).

A ferramenta _keytool_ é empregada para acrescentar o certificado da autoridade
certificadora que criou o certificado empregado pelos serviços oferecidos pelo
DATASUS. Desta forma, uma aplicação em Java terá "confiança" no certificado
empregado pelos servidores do DATASUS. Veja [autenticação](../autenticacao.md) para detalhes.
