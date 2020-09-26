---
id: passo-a-passo
title: Passo a passo 
sidebar_label: Passo a passo
---

O **Guia de Integração RNDS** é dirigido a um [público-alvo](./publico-alvo) específico com foco na obrigatoriedade de 
notificação de resultados de exames SARS-CoV-2 (consulte o [contexto](./contexto) para detalhes).

>A integração necessária entre estabelecimentos de saúde (laboratórios) e o Ministério da Saúde é o foco deste guia, e realizada por meio da [RNDS](../rnds/rnds).

Abaixo segue uma orientação, passo a passo, das atividades a serem desempenhadas para tal integração. Antes, contudo, é conveniente entender o [contexto](./contexto), o [público-alvo](./publico-alvo) do _Guia_ e o que a [RNDS](../rnds/rnds) oferece e estabelece para a correta integração. Adicionalmente, os passos estão distribuídos entre dois atores: [gestor](./gestor) e profissional de 
[TI](../ti/ti). 

A sequência sugerida é:
1. O gestor obtém o [certificado digital](../gestor/certificado).
1. O gestor [cria uma conta gov.br](../gestor/gov.br), caso não possua uma.
1. O gestor [solicita acesso](../gestor/portal) à RNDS, e aguarda a resposta do DATASUS. 
1. O gestor obtém o [identificador do solicitante](../gestor/identificador) após a aprovação da solicitante de acesso.
1. O profissional de TI [interage](../ti/postman) com os serviços (entradas/saídas) oferecidos e, dessa forma, os conhece e os compreende.
1. O profissional de TI [desenvolve](../ti/si) o software necessário para a integração com a RNDS.
1. O profissional de TI [homologa](../ti/homologar) a integração realizada via o software desenvolvido (passo anterior).
1. O profissional de TI [coloca em produção](../ti/producao) o software que integra o estabelecimento de saúde à RNDS (e homologado no passo anterior).

