---
id: passo-a-passo
title: Passo a passo
sidebar_label: Passo a passo
---

O **Guia de Integração RNDS** tem como objetivo orientar um [público-alvo](./publico-alvo) específico visando satisfazer a obrigatoriedade de
notificação de resultados de exames (SARS-CoV-2) ao Ministério da Saúde (consulte o [contexto](./contexto) para detalhes).

> A integração necessária entre estabelecimentos de saúde (laboratórios) e o Ministério da Saúde é o foco deste guia. Esta integração é realizada por meio da [RNDS](../rnds/rnds).

Abaixo segue uma orientação, passo a passo, das atividades a serem desempenhadas para tal integração. Antes, contudo, é sugerido compreender o [público-alvo](./publico-alvo) e o [contexto](./contexto) do _Guia_, além do papel da [RNDS](../rnds/rnds) nessa integração.

Os passos estão distribuídos entre dois atores: o [gestor](../gestor/gestor) da instituição de saúde e profissional de
[TI](../ti/ti) dessa instituição.

A sequência sugerida é:

1. O gestor deve obter o [certificado digital](../gestor/certificado).
1. O gestor deve [criar uma conta gov.br](../gestor/gov.br), caso não possua uma.
1. O gestor deve [solicitar acesso](../gestor/portal) à RNDS, e aguardar a resposta do DATASUS.
1. O gestor deve obter o [identificador do solicitante](../gestor/identificador) após a aprovação da solicitação de acesso pelo DATASUS.
1. O profissional de TI deve [interagir](../ti/conhecer) com os serviços (entradas/saídas) oferecidos e, dessa forma, conhecê-los e compreendê-los.
1. O profissional de TI deve [desenvolver](../ti/si) o software necessário para a integração com a RNDS.
1. O profissional de TI deve produzir as evidências necessárias para [homologar](../ti/homologar) a integração implementada pelo software desenvolvido (passo anterior).
1. O gestor deve solicitar acesso ao ambiente de produção e aguardar a resposta do DATASUS.
1. O profissional de TI deve [colocar em produção](../ti/producao) o software que integra o estabelecimento de saúde à RNDS (e homologado no passo anterior).
