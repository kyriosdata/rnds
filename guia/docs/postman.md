---
id: postman
title: Primeiros contatos
sidebar_label: Primeiros contatos
---

A adoção do padrão FHIR significa independência tecnológica dos laboratórios em relação à RNDS. Ou seja, a RNDS não impõe, restringe ou orienta decisões
no escopo do ecossistema do laboratório. Dito de outra forma, a infraestrutura
do laboratório pode estar disponível no próprio laboratório, em "nuvem", usar
Java, C#, Javascript no desenvolvimento do seu ecossistema ou outro. Em algum momento, contudo, requisições _https_ devem partir do ecossistema do laboratório e atingir o [ambiente](./ambientes) de produção da RNDS (neste guia faremos uso do ambiente de homologação).

### Pré-requisitos

- Certificado digital. O arquivo correspondente deve estar disponível, é um arquivo com a extensão **.pfx**, aqui será referenciado por **certificado.pfx**.
- Credenciamento homologado. O credenciamento é feito pelo [portal de serviços](https://servicos-datasus.saude.gov.br/). É preciso aguardar a homologação, necessária para se ter acesso ao [ambiente](../ambientes) de homologação.
- Identificador do laboratório fornecido pela RNDS. Este identificador é criado quando a RNDS homologa o credenciamento.
- CNES. O CNES do laboratório.

A documentação das requisições está disponível em https://documenter.getpostman.com/view/215332/TVCiUn6P.
