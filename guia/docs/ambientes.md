---
id: ambientes
title: Ambientes
sidebar_label: Ambientes
---

O padrão FHIR, adotado pela RNDS, possui uma RESTFul API bem-definida.
O Software de Integração a ser criado por um estabelecimeto de
saúde, portanto, fará uso desta API para se integrar à RNDS.

A API é oferecida em dois ambientes, um de homologação (desenvolvimento e testes) 
e outro de produção. Em ambos os ambientes são oferecidos dois 
endereços: um para obtenção do _token_ de acesso (_Auth_) e
outro para os serviços (_EHR_) propriamente ditos para troca de informações em saúde, conforme ilustrado abaixo.

A obtenção deste _token_ exige a disponibilidade
do Certificado Digital do estabelecimento de saúde em questão, ou seja, um certificado
ICP-Brasil (e-CPF ou e-CNPJ).

![img](../static/img/ambientes.png)

### Ambiente de homologação (endereços)

O ambiente de homologação existe para testes e experimentações.

| Função       | Endereço                                                     |
| :----------- | :----------------------------------------------------------- |
| Auth (único) | **https<span>:</span>//ehr-auth-hmg.saude.gov.br/api/token** |
| EHR  (único) | **https<span>:</span>//ehr-services.hmg.saude.gov.br/api**   |

### Ambiente de produção (endereços)

O ambiente de produção coloca à disposição os serviços que, de fato,
enviam e recuperam informações em saúde "reais".

| Função           | Endereço                                                          |
| :--------------- | :---------------------------------------------------------------- |
| Auth (único)     | **https<span>:</span>//ehr-auth.saude.gov.br/api/token**          |
| EHR (por estado) | **https<span>:</span>//&lt;UF&gt;-ehr-services.saude.gov.br/api** |

O endereço para obtenção do _token_ de acesso é único para todo o Brasil.
Por outro lado, há um endereço para cada estado da federação para requisição
dos serviços FHIR. Neste caso, cada laboratório, conforme o CNES em questão, fará uso do estado correspondente.

O Laboratório Rômulo Rocha, por exemplo, localizado no Estado de Goiás (CNES 2337991), fará uso do
endereço **https<span>:</span>//go-ehr-services.saude.gov.br/api**. Em outro exemplo, se o laboratório está em Minas Gerais, então
o endereço que deve ser obrigatoriamente empregado é **https<span>:</span>//mg-ehr-services.saude.gov.br/api**, e assim por diante.
