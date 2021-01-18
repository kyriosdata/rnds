---
id: ambientes
title: Ambientes
sidebar_label: Ambientes
---

O software a ser criado por um estabelecimeto de
saúde para integração com a RNDS fará uso da RESTful API FHIR.
Esta API é oferecida em dois ambientes, um de homologação (desenvolvimento e testes) e outro de produção.

Cada um destes ambientes é
acessível pelo estabelecimento de saúde por meio de dois endereços: um para obtenção do _token_ de acesso (_Auth_) e
outro para os serviços (_EHR_) propriamente ditos para troca de informações em saúde, conforme ilustrado abaixo.

![img](../static/img/ambientes.png)

:::caution IMPORTANTE
A obtenção do _token_ de acesso exige a disponibilidade
do Certificado Digital do estabelecimento de saúde em questão. O
certificado deve ser ICP-Brasil (e-CPF ou e-CNPJ).
:::

### Ambiente de homologação (endereços)

O ambiente de homologação existe para testes e experimentações.

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
