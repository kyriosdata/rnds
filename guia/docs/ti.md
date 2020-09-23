---
id: ti
title: Responsável pela TI
sidebar_label: Atribuições
---

A integração com a RNDS exige atribuições distribuídas entre dois
papéis. Um deles é o [gestor](./gestor) do estabelecimento de saúde.
O outro é o responsável pela TI, que não necessariamente executa
as atividades técnicas. Outros termos geralmente utilizados incluem
programador, integrador, desenvolvedor de software e engenheiro de software.

![img](../static/img/ti.png)

## Atribuições da TI
As atribuições do responsável pela TI, detalhadas abaixo, são:
(a) desenvolver o software de integração; (b) homologar o software e 
(c) colocar a integração em produção.


### Desenvolver

A proposta e desenvolvimento do _software_ de integração para um dado laboratório depende do contexto em questão, contudo, invariavelmente, o _software_ a ser produzido terá que realizar funções bem-definidas. Veja aqui tais [funções](./si).

A execução satisfatória desta atribuição resulta em:

- _Software_ disponível para integração entre o laboratório e a RNDS.
- Testes de integração com a RNDS.
- _Baseline_ formada pelo código produzido, testes,
  configuração, documentação e outros itens pertinentes devidamente identificados.

### Homologar

O _software_ produzido deverá ser experimentado no ambiente de homologação. Esta experimentação deverá gerar evidências de que se integra satisfatoriamente à RNDS.

A execução satisfatória desta atribuição resulta em:

- Execução satisfatória dos testes de integração.
- Registro dos resultados dos testes de integração.
- Disponibilizar tais registros para o responsável pelo laboratório. Isto é necessário para o responsável pelo laboratório requisitar acesso ao ambiente de produção.

### Colocar em produção

Algumas configurações são alteradas, como os
endereços dos serviços do ambiente de produção.

A execução satisfatória desta atribuição resulta em:

- _Software_ produzido e configurado corretamente para acesso ao ambiente de produção.
- _Software_ interagindo com o ambiente de produção.
