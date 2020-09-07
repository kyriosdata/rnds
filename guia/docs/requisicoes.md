---
id: requisicoes
title: Requisições
sidebar_label: Requisições
---

Há várias formas de se construir e enviar uma requisição RESTful a um
servidor.

O servidor de testes [Hapi FHIR](http://hapi.fhir.org/) oferece uma interface gráfica por meio de um navegador. É útil para experimentação e para obter detalhes de como criar corretamente uma requisição. Uma alternativa bem mais técnica é o aplicativo oferecido via linha de comandos, [cURL](https://en.wikipedia.org/wiki/CURL), sigla para _client URL_. A abordagem oferecida no guia, contudo, baseia-se no emprego da ferramenta [Postman](./roteiro).

### Postman

A ferramenta [Postman](https://www.getpostman.com/downloads/) é uma alternativa
gráfica para montar requisições, executá-las, e configurar testes, dentre outras funções, o que a torna uma espécie de referência no desenvolvimento de APIs.

### Desenvolvedores via código

Desenvolvedores escrevem código. O acesso à RESTful API FHIR via código
é apoiada por várias bibliotecas. Abaixo seguem algumas delas:

- [.Net](http://ewoutkramer.github.io/fhir-net-api/client-setup.html)
- [JavaScript](https://github.com/smart-on-fhir/client-js)
- [Java](https://github.com/FirelyTeam/fhirstarters/tree/master/java/hapi-fhirstarters-client-skeleton)
