---
id: introducao
title: Introdução
sidebar_label: Introdução
---

Na perspectiva de um estabelecimento de saúde, a Rede Nacional de Dados em Saúde (RNDS) oferece serviços
para a interoperabilidade em saúde no território nacional. No Brasil, é por meio da RNDS que um estabelecimento em saúde disponibiliza informação que será consumido por outro.

Quando um estabelecimento de saúde se integra à RNDS, cria-se a possibilidade dele contribuir com informações em saúde pertinentes aos usuários que assiste,
bem como consumir informações geradas por outros estabelecimentos.

Conforme ilustrado abaixo, a integração com a RNDS segue o padrão [FHIR](./glossario#fhir). Isso significa "independência tecnológica" dos estabelecimentos de saúde em relação à RNDS, que não impõe, não restringe e nem tampouco orienta decisões no escopo do ecossistema do estabelecimento de saúde.

Em algum momento, contudo, requisições _https_, obedecendo o padrão FHIR e a [adaptação](./definicoes) definida pela RNDS, devem partir do estabelecimento de saúde e atingir o [ambiente](./ambientes) de produção da RNDS, seja para enviar ou requisitar informação em saúde.

![interoperabilidade](../static/img/rnds-brasil.png)

A interoperabilidade, no momento, contempla a notificação de resultados de exames de SARS-CoV-2. O conjunto será estendido ao longo do tempo, contudo,
tendo em vista a adoção do padrão FHIR, integrar para notificar um resultado de exame é "similar" à submissão de um Registro de Atendimento Clínico (RAC)
ou Sumário de Alta (SA), dentre outros.

## Guia

O **Guia de Integração RNDS** tem como objetivo orientar o desenvolvimento
de solução tecnológica para a integração com a RNDS. Esta integração é
ilustrada pela notificação obrigatória de resultados de exames (SARS-CoV-2) ao Ministério da Saúde pelos laboratórios em território nacional.

Dois papéis definem o [público-alvo](./rel/intro/publico-alvo) deste _Guia_: (a) gestor (responsável pelo laboratório) e (b) integrador (tecnologia da informação).

> A integração entre estabelecimentos de saúde (laboratórios) e a [RNDS](../rnds/rnds) (Ministério da Saúde) é o foco dos exemplos deste guia.
