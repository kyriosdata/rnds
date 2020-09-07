---
id: servicos
title: Catálogo de Serviçøs
sidebar_label: Catálogo de Serviços
---

Há um serviço (_web service_) de _segurança_ e os demais são de _saúde_. 

O serviço de segurança obtém o _token_ de acesso exigido para execução dos demais serviços. Este _token_ substitui o emprego de autenticação baseada em usuário e uma senha secreta por certificado digital. O certificado digital, portanto, é empregado para autenticar a origem ou em nome de quem as requisições serão submetidas. 

Os serviços (_web services_) de saúde implementam a troca de informação em saúde. Estes serviços são o meio para se atingir a deseja interoperabilidade. 

### Segurança (_web service_)

| Método | Path                      | Entrada | Saida | Descrição |
|--------|---------------------------|---------|-------|-----------|
| GET    | /token          |         |       |           |


### Saúde (_web services_) 

| Método | Path                      | Entrada | Saida | Descrição |
|--------|---------------------------|---------|-------|-----------|
| GET    | /fhir/r4/Patient          |         |       |           |
| GET    | /fhir/r4/Organization     |         |       |           |
| GET    | /fhir/r4/Practitioner     |         |       |           |
| GET    | /fhir/r4/PractitionerRole |         |       |           |
| POST    | /fhir/r4/Bundle     |         |       |           |
| POST    | /fhir/r4/contexto-atendimento     |         |       
