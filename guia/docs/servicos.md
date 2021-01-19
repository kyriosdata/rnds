---
id: servicos
title: Serviços
sidebar_label: Serviços
---

A integração com a RNDS é realiza por meio de _web services_, ou serviços, por simplicidade. São estes serviços que implementam a desejada interoperabilidade em saúde no Brasil.

Os serviços encontram-se disponíveis por meio de dois ambientes. Consulte a documentação dos [ambientes](ambientes) para detalhes, inclusive os endereços dos serviços. Abaixo seguem os métodos HTTP e _paths_ dos serviços.

O integrador, contudo, precisa ir além destas informações sobre os serviços. É preciso, por exemplo, detalhes dos _headers_ das requisições, das respostas, e o formato do _payload_, dentre outras. Para isto, a sugestão é experimentar os serviços por meio de um [roteiro](rel/ti/conhecer) especificamente produzido para esta finalidade.

### Segurança (_web services_)

| Método | Path                      | Descrição                                  |
| ------ | ------------------------- | ------------------------------------------ |
| GET    | /api/token                | Obtém _token_ de acesso                    |
| POST   | /api/contexto-atendimento | Obtém _token_ para contexto de atendimento |

### Saúde (_web services_)

| Método | Path                          | Descrição                                                                 |
| ------ | ----------------------------- | ------------------------------------------------------------------------- |
| GET    | /api/fhir/r4/Patient          | Obter informações sobre paciente.                                         |
| GET    | /api/fhir/r4/Organization     | Obter informações sobre um estabelecimento de saúde ou outra organização. |
| GET    | /api/fhir/r4/Practitioner     | Obter informações sobre profissional de saúde                             |
| GET    | /api/fhir/r4/PractitionerRole | Obter informações sobre papéis desempenhados por profissionais de saúde.  |
| POST   | /api/fhir/r4/Bundle           | Enviar resultado de exame                                                 |
| POST   | /api/fhir/r4/Bundle           | Substituir resultado de exame                                             |
