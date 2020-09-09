---
id: servicos
title: Catálogo de Serviços
sidebar_label: Catálogo de Serviços
---

Dois serviços (_web services_) são de _segurança_ e os demais enviam informações para a RNDS ou recuperam informações de _saúde_ disponibilizadas pela RNDS.

Os serviços de segurança são intermediários, é a partir deles que se obtém
a autenticação necessária para acesso aos demais serviços. Em particular,
substituem o emprego de autenticação baseada em usuário/senha por certificado digital (estratégia considerada mais segura).

Os serviços (_web services_) de saúde implementam a troca de informação em saúde. Estes serviços são o meio para se atingir a desejada interoperabilidade em saúde no Brasil.

Abaixo segue uma visão panorâmica dos serviços e em [Interagindo com a RNDS](./tools/postman) há orientações detalhadas.

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
