---
id: passo-a-passo
title: Passo a passo
sidebar_label: Passo a passo
---

O processo de credenciamento de um estabelecimento de saúde
junto à RNDS é realizado em duas fases. Na primeira o estabelecimento requisita acesso ao ambiente de homologação. Na segunda, o estabelecimento requisita acesso ao ambiente de produção. Quando esta última é
concedida, o estabelecimento está autorizado a trocar informações com a RNDS.

Antes de apresentar detalhes destas fases, contudo, a sugestão é se ambientar com o conteúdo da [introdução](./introducao) e do [público-alvo](publico-alvo/publico-alvo), pois oferecem um contexto para o entendimento do Guia.

Os detalhes destas fases são fornecidos abaixo, por meio de um fluxo típico, representativo de vários cenários. Variações são naturais, pois a maioria das atividades é realizada pelo gestor e o integrador, ou seja, pelo estabelecimento de saúde.

## Fluxo típico

1. O gestor deve [obter o certificado digital](publico-alvo/gestor/certificado), se não possuir um.
1. O gestor deve [criar uma conta gov.br](publico-alvo/gestor/gov.br), caso não possua uma.
1. O gestor deve [solicitar acesso](publico-alvo/gestor/portal) à RNDS (primeira fase). A solicitação de acesso é feita pelo Portal de Serviços cujo acesso exige uma conta gov.br (passo anterior). São requisitadas várias informações sobre o estabelecimento de saúde, inclusive o certificado digital (primeiro passo).
1. O gestor deve [obter o identificador do solicitante](publico-alvo/gestor/identificador), ou seja, um identificador fornecido pelo DATASUS para o estabelecimento de saúde em questão, cujo acesso ao ambiente de homologação é concedido.
1. O integrador deve [ambientar-se](publico-alvo/ti/conhecer) com os serviços (entradas/saídas) oferecidos e, dessa forma, conhecê-los e compreendê-los. Observe que esta atividade pode ser iniciada antes dos passos anteriores.
1. O integrador deve [desenvolver](conector) a solução tecnológica, aqui chamada de conector, para a integração com a RNDS.
1. O integrador deve verificar a conformidade do conector com o contrato estabelecido para a integração com a RNDS.
1. O integrador deve produzir as evidências necessárias para [homologar](publico-alvo/ti/homologar) o conector desenvolvido.
1. O gestor deve solicitar acesso ao ambiente de produção e aguardar a resposta do DATASUS.
1. O integrador deve [colocar em produção](publico-alvo/ti/producao) o software que realiza a integração entre o estabelecimento de saúde e a RNDS (e homologado no passo anterior).

## Diagrama

O diagrama abaixo, na notação BPMN, ilustra o fluxo de atividades de credenciamento, distribuídas entre três atores: (i) DATASUS; (ii) Gestor e (iii) Integrador.

![](../static/img/rnds-processo-credenciamento.png)
