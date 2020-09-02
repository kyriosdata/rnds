---
id: si
title: Software de Integração
sidebar_label: Software de Integração
---

O _software_ de integração com a RNDS é específico para esta finalidade e, portanto, demanda esforço correspondente. As duas seções seguintes
apresentam, respectivamente, tal _software_ na perspectiva dos principais atores e as funções que deve implementar.

## Funções do software de integração

O _software_ de integração com a RNDS, a ser desenvolvido por cada laboratório, deve desempenhar um conjunto de funções bem-definidas.
As funções, os fluxos entre elas e os dados necessários são exibidas abaixo.

![img](../static/img/rnds-dfd.png)

Cada função é definida e classificada quanto à fase em que é executada (preparação ou entrega).

- PREPARAÇÃO
  1.  **Filtrar**. Dentre os dados produzidos para um laudo, aqueles necessários são selecionados.
  1.  **Mapear**. código empregados pelo laboratório e/ou transformações de dados necessários para se adequar às exigências da RNDS;
  1.  **Empacotar** os dados na representação a ser utilizada para envio (JSON).
  1.  **Verificar** o documento JSON correspondente ao laudo a ser enviado.
- ENTREGA
  1.  **Autenticar**. Obter a chave de acesso aos serviços.
  1.  **Enviar**. Atividade que notifica o resultado de um exame, conforme
      padrões estabelecidos pela RNDS, ao Ministério da Saúde.

A figura abaixo ilustra a classificação das funções, a ordem de execução destas funções e os dois ambientes (de homologação e produção).
A fase de preparação reúne funções "locais" ao laboratório, não demandam interação com um dos ambientes da RNDS. Por outro lado, as funções da
fase de entrega, _Autenticar_ e _Enviar_, depende do acesso a um dos
ambientes da RNDS.

![img](../static/img/desenvolvedor.png)
