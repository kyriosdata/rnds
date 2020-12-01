---
id: introducao
title: Introdução
sidebar_label: Introdução
---

O **Guia de Integração RNDS** tem como objetivo orientar o desenvolvimento
de solução tecnológica para a notificação obrigatória de resultados de exames (SARS-CoV-2) ao Ministério da Saúde.

O [público-alvo](./publico-alvo) deste _Guia_ é formado por gestores
de estabelecimentos de saúde (laboratórios) e profissionais de TI. Ambos
são necessários para desenvolver a solução tecnológica que conecta o estabelecimento de saúde à [RNDS](../rnds/rnds). É por meio desta conexão que o estabelecimento
submete as notificações ao Ministério da Saúde. Consulte o [contexto](./contexto) para detalhes.

> A integração obrigatória entre estabelecimentos de saúde (laboratórios) e a [RNDS](../rnds/rnds) (Ministério da Saúde) é o foco deste guia.

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
1. O profissional de TI deve [colocar em produção](../ti/producao) o software que realiza a integração entre o estabelecimento de saúde e a RNDS (e homologado no passo anterior).
