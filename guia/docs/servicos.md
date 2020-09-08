---
id: servicos
title: Catálogo de Serviços
sidebar_label: Catálogo de Serviços
---

Dois serviços (_web services_) são de _segurança_ e os demais enviam ou recuperam informações de _saúde_.

O serviço de segurança obtém o _token_ de acesso exigido para execução dos demais serviços. Este _token_ substitui o emprego de autenticação baseada em usuário e uma senha secreta por certificado digital. O certificado digital, portanto, é empregado para autenticar a origem, ou em nome de quem, as requisições serão submetidas.

Os serviços (_web services_) de saúde implementam a troca de informação em saúde. Estes serviços são o meio para se atingir a desejada interoperabilidade em saúde no Brasil.

### Segurança (_web services_)

| Método | Path                      | Descrição                                  |
| ------ | ------------------------- | ------------------------------------------ |
| GET    | /api/token                | Obtém _token_ de acesso                    |
| POST   | /api/contexto-atendimento | Obtém _token_ para contexto de atendimento |

### Saúde (_web services_)

| Método | Path                          | Descrição                     |
| ------ | ----------------------------- | ----------------------------- |
| GET    | /api/fhir/r4/Patient          |                               |
| GET    | /api/fhir/r4/Organization     |                               |
| GET    | /api/fhir/r4/Practitioner     |                               |
| GET    | /api/fhir/r4/PractitionerRole |                               |
| POST   | /api/fhir/r4/Bundle           | Enviar resultado de exame     |
| POST   | /api/fhir/r4/Bundle           | Substituir resultado de exame |
