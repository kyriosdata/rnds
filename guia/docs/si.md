---
id: si
title: Desenvolver
sidebar_label: Desenvolver
---

O _software_ de integração com a RNDS é específico para esta finalidade e, portanto, demanda esforço correspondente de profissional de TI. Abaixo segue uma análise preliminar e genérica tanto das funcionalidades quando do _design_ de um software que atende tais funcionalidades para a integração. Cada cenário de integração é específico e, portanto, talvez você prefira saltar o que segue e ir diretamente para as 
disponibilizadas para apoiar a integração.

As funcionalidades esperadas são fornecidas na seção seguinte. O _design_ de um _software_ correspondente e a implementação, por outro lado, dependem de um número significativo de variáveis, por exemplo, a linguagem de programação, o sistema de software utilizado pelo laboratório e
a política a ser utilizada para envio dos resultados, dentre outros.
Em consequência, só podem ser realizados caso a caso por cada laboratório.

As especificidades de cada laboratório, contudo, não impedem a produção de um _design_ para orientar e, inclusive, a implementação correspondente pronta para ser reutilizada por desenvolvedores (profissionais de TI), onde for o caso.

Este documento apresenta as funcionalidas, o _design_ para um cenário "clássico", a implementação correspondente e ferramentas para desenvolvedores.

## Funcionalidades

O _software_ de integração com a RNDS, a ser desenvolvido por cada laboratório, fará uso de vários serviços oferecidos. Dois deles são
representativos e estão explicitamente indicados na figura abaixo.

![img](../static/img/rnds-uc.png)

O diagrama acima contempla _Obter token de acesso_ e _Enviar resultado de exame_, não apenas por serem os principais, mas porque cobrem tudo o
que é necessário nos demais serviços.

Na perspectiva de processos (funções) e do fluxo de informações entre eles, o diagrama correspondente é fornecido abaixo. Aqueles destacados estão diretamente associados aos casos de uso identificados acima, ou seja, fazem parte do escopo a ser implementado. Os demais processos (funções) são necessários, mas ao mesmo tempo, dependentes do ecossistema do laboratório.

![img](../static/img/rnds-dfd.png)

Cada função é definida e classificada quanto à fase em que é executada (preparação ou entrega).

- PREPARAÇÃO

  1.  **Filtrar**. Seleciona os dados de um resultado de exame a partir dos quais aqueles exigidos pela RNDS serão produzidos.
  1.  **Mapear**. Realiza a conversão e/ou mapeamento necessário entre os dados selecionados (filtrados) e aqueles no formato exigido pela RNDS.
  1.  **Empacotar**. Cria a representação JSON dos dados correspondentes a um
      resultado.

- ENTREGA
  1.  **Verificar**. Confere se o empacotamento do resultado a ser enviado está consistente com especificação da RNDS.
  1.  **Autenticar**. Obtém chave para acesso aos serviços da RNDS.
  1.  **Enviar**. Notifica o resultado de um exame à RNDS.

A figura abaixo ilustra os processos e a classificação deles, além de indicar que dois processos, via internet, interagem com as portas Auth e EHR, oferecidas pela RNDS. Detalhes são fornecidos em [Ambientes](./ambientes).

![img](../static/img/desenvolvedor.png)

## Design

Dentre as funções atribuídas ao _Software de Integração_, ao todo seis, conforme seção anterior, duas delas não são contempladas aqui: (a) filtrar e (b) mapear. Estas funções são bem específicas e dependentes do ecossistema disponível no laboratório.

### Implantação

Na perspectiva de implantação (_deployment_), a figura abaixo
ilustra uma possível organização do ecossistema de software
utilizado por um laboratório, e sua integração com a RNDS.

![img](../static/img/rnds-deployment.png)

Na figura, _Software de integração_ é
um componente isolado, distinto de um "Sistema Usado pelo Laboratório".
Em um dado laboratório, contudo, pode não existir um "Sistema Usado pelo Laboratório", ou até existir, mas a TI do laboratório propor um _design_ distinto no qual as funções aqui atribuídas ao _Software de Integração_
seriam incorporadas pelo "Sistema Usado pelo Laboratório".

Adicionalmente, se esta figura, por acaso, reflete parte do _design_ adotado por algum laboratório, ainda existem vários detalhes omitidos e relevantes para
o _design_ da integração. Por exemplo, a forma de obtenção dos dados
pertinentes aos resultados de exames e atualmente mantidos pelo "Software Usado pelo Laboratório", conforme nota pertinente na figura acima.

> IMPORTANTE: a figura acima, embora possa inspirar o _design_ da
> integração de um laboratório com a RNDS, apenas registra uma possibilidade cujo objetivo é orientar desenvolvedores de software acerca de questões pertinentes à tal integração.
