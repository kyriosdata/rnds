---
id: servicos
title: Serviços
sidebar_label: Serviços
---

São serviços (_web services_) oferecidos pela RNDS que implementam a desejada interoperabilidade em saúde no Brasil.

Os endereços dos serviçoes estão identificados na documentação dos [ambientes](ambientes). A experimentação dos serviços é recomendada antes da produção do software de integração. Por meio da experimentação pode-se ambientar com _headers_ exigidos, retornados, o conteúdo do _payload_ por requisição e outros. Preparamos um [roteiro](rel/ti/conhecer) para facilitar.

Abaixo segue a identificação dos serviços disponibilizados e distribuídos em serviços de segurança e serviços de saúde. Os serviços de segurança substituem o emprego de autenticação baseada em usuário/senha por [certificado digital](rel/gestor/certificado) (estratégia considerada mais segura). A partir deles obtém-se _token_ de acesso exigido nas requisições aos demais serviços.

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
