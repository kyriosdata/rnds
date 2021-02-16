---
id: ambientes
title: Ambientes
sidebar_label: Ambientes
---

A RNDS é acessível a um software externo por meio de dois ambientes:
o ambiente de homologação e o ambiente de produção.

![img](../../static/img/ambientes.png)

## Qual ambiente usar?

Durante o desenvolvimento ou teste de software que se conecta à RNDS,
o ambiente a ser utilizado é o ambiente de homologação.

E quando o software for considerado pronto para cumprir sua função de integração
com a RNDS? Neste caso, evidências deverão ser coletadas e submetidas pelo
estabelecimento de saúde em questão para serem apreciadas pelo DATASUS.
Caso a resposta seja positiva, então o acesso ao ambiente de produção
é disponibilizado. Isto é detalhado no processo de
[homologação](../publico-alvo/ti/homologar).

Após aprovação da requisição de acesso ao ambiente de produção, parte do processo de [homologação](../publico-alvo/ti/homologar), o software de integração do estabelecimento de saúde em questão poderá acessá-lo.

## Integração com a RNDS usa dois endereços

Tanto o ambiente de homologação quanto o de produção oferecem dois endereços para acesso: um para finalidade de segurança, denominado _Auth_, e outro para acesso aos serviços de troca de informações em saúde, denominado de _EHR_.

O endereço _Auth_ é exclusivo para obtenção do _token_ de acesso e exige o emprego de [certificado digital](../publico-alvo/gestor/certificado). Este _token_ de acesso é exigido como parte de cada uma das requisições enviadas ao endereço _EHR_. Isto é feito por meio de _header_ próprio, **X-Authorization-Server**. O valor deste _header_ assim como o conteúdo do outro _header_ de segurança exigido, **Authorization**, são discutidos em detalhes na definição dos [serviços](../publico-alvo/ti/conhecer).

## Endereços

Os endereços para acesso à RNDS são fornecidos por ambiente. Convém destacar que detalhes da integração, por exemplo, o emprego do protocolo _https_, dentre outros, são fornecidos em [Serviços](servicos).

### Ambiente de homologação (endereços)

O ambiente de homologação existe para testes e experimentações. Este ambiente é único para todo o Brasil.

| Função       | Endereço                          |
| :----------- | :-------------------------------- |
| Auth (único) | **ehr-auth-hmg.saude.gov.br**     |
| EHR (único)  | **ehr-services.hmg.saude.gov.br** |

### Ambiente de produção (endereços)

O ambiente de produção coloca à disposição os serviços que, de fato,
enviam e recuperam informações em saúde.

| Função              | Endereço                         |
| :------------------ | :------------------------------- |
| Auth (único)        | **ehr-auth.saude.gov.br**        |
| EHR (por estado)    |                                  |
| Acre                | **ac-ehr-services.saude.gov.br** |
| Alagoas             | **al-ehr-services.saude.gov.br** |
| Amapá               | **ap-ehr-services.saude.gov.br** |
| Amazonas            | **am-ehr-services.saude.gov.br** |
| Bahia               | **ba-ehr-services.saude.gov.br** |
| Ceará               | **ce-ehr-services.saude.gov.br** |
| Distrito Federal    | **df-ehr-services.saude.gov.br** |
| Espírito Santo      | **es-ehr-services.saude.gov.br** |
| Goiás               | **go-ehr-services.saude.gov.br** |
| Maranhão            | **ma-ehr-services.saude.gov.br** |
| Mato Grosso         | **mt-ehr-services.saude.gov.br** |
| Mato Grosso do Sul  | **ms-ehr-services.saude.gov.br** |
| Minas Gerais        | **mg-ehr-services.saude.gov.br** |
| Pará                | **pa-ehr-services.saude.gov.br** |
| Paraíba             | **pb-ehr-services.saude.gov.br** |
| Paraná              | **pr-ehr-services.saude.gov.br** |
| Pernambuco          | **pe-ehr-services.saude.gov.br** |
| Piauí               | **pi-ehr-services.saude.gov.br** |
| Rio de Janeiro      | **rj-ehr-services.saude.gov.br** |
| Rio Grande do Norte | **rn-ehr-services.saude.gov.br** |
| Rio Grande do Sul   | **rs-ehr-services.saude.gov.br** |
| Rondônia            | **ro-ehr-services.saude.gov.br** |
| Roraima             | **rr-ehr-services.saude.gov.br** |
| Santa Catarina      | **sc-ehr-services.saude.gov.br** |
| São Paulo           | **sp-ehr-services.saude.gov.br** |
| Sergipe             | **se-ehr-services.saude.gov.br** |
| Tocantins           | **to-ehr-services.saude.gov.br** |

O endereço para obtenção do _token_ de acesso é único para todo o Brasil.
Por outro lado, há um endereço para cada estado da federação para requisição
dos serviços FHIR. Neste caso, cada laboratório, conforme o CNES em questão, fará uso do estado correspondente.

O Laboratório Rômulo Rocha, por exemplo, localizado no Estado de Goiás (CNES 2337991), fará uso do
endereço **go-ehr-services.saude.gov.br**. Em outro exemplo, se o laboratório está em Minas Gerais, então
o endereço que deve ser obrigatoriamente empregado é **mg-ehr-services.saude.gov.br**, e assim por diante.
