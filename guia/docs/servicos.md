---
id: servicos
title: Serviços
sidebar_label: Serviços
---

Dentre os serviços (_web services_) oferecidos pela RNDS, dois são de _segurança_ e os demais enviam informações para a RNDS ou recuperam informações de saúde disponibilizadas pela RNDS.

Os serviços de segurança substituem o emprego de autenticação baseada em usuário/senha por certificado digital (estratégia considerada mais segura). A partir deles obtém-se a autenticação necessária para acesso aos demais serviços.

Os serviços de saúde implementam a troca de informação em saúde. Estes serviços são o meio para se atingir a desejada interoperabilidade em saúde no Brasil.

Abaixo segue uma visão panorâmica dos serviços oferecidos. Desenvolvedores, contudo, terão que [interagir e conhecer detalhes](rel/ti/conhecer).

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
