---
id: ti
title: Integrador
sidebar_label: Atribuições
---

Integrador é o responsável pela TI do estabelecimento de saúde. O
integrador orienta e/ou executa ações necessárias para o desenvolvimento
da solução tecnológica (conector) para a integração com a RNDS.

Outras denominações: engenheiros de Software, programadores, desenvolvedores, técnicos de TI, técnicos de departamentos de informação em saúde, bem como coordenadores de setor de tecnologia das secretarias municipais de saúde.

A integração com a RNDS exige atribuições distribuídas entre dois
papéis. Um deles é o [gestor](../gestor/gestor) do estabelecimento de saúde.
O outro é o responsável pela TI, que não necessariamente executa
as atividades técnicas. O [público-alvo](../intro/publico-alvo) fornece detalhes.
A intenção aqui é orientar a TI do estabelecimento de saúde.

![img](../../../static/img/ti.png)

As atribuições da TI, conforme ilustradas acima, incluem:
(a) conhecer os serviços oferecidos; (b) desenvolver o software de integração; (c) homologá-lo e (d) colocá-lo em produção.

### Conhecer os serviços

A ambientação com os serviços oferecidos pela RNDS é considerada imprescindível. Há várias formas para adquirir o conhecimento e
a compreensão acerca dos serviços. A mais recomendada é interagir com eles,
saber o que eles exigem como _payload_, _headers_ e a saída
esperada e código de retorno, dentre outros. Para tal, nada melhor
do que seguir as orientações para [interação com o ambiente de homologação](./conhecer).

### Desenvolver

A proposta e desenvolvimento de um _software_ de integração para um dado laboratório depende do contexto em questão, por exemplo, um estabelecimento que já faz uso de um sistema de informação, provavelmente irá considerar um projeto de manutenção (evolução) do sistema que já faz uso. De qualquer forma, invariavelmente, o _software_ a ser produzido terá que realizar [funções bem-definidas](./si).

A execução satisfatória desta atribuição resulta em:

- _Software_ disponível para integração do estabelecimento de saúde com a RNDS.
- Testes de integração com a RNDS.
- _Baseline_ formada pelo código produzido, testes,
  configuração, documentação e outros itens pertinentes devidamente identificados.

### Homologar

O _software_ produzido deverá ser experimentado no ambiente de homologação. Esta experimentação deverá gerar evidências de que se integra satisfatoriamente à RNDS.

A execução satisfatória desta atribuição resulta em:

- Execução satisfatória dos testes de aceitação.
- Registro dos resultados dos testes de aceitação.
- Disponibilizar tais registros para o gestor. Isto é necessário para o gestor possa requisitar acesso ao ambiente de produção.

### Colocar em produção

Algumas configurações são alteradas, como os
endereços dos serviços do ambiente de produção.

A execução satisfatória desta atribuição resulta em:

- _Software_ produzido e configurado corretamente para acesso ao ambiente de produção.
- _Software_ interagindo com o ambiente de produção.
