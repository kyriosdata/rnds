---
id: fhirserver
title: Servidor FHIR
sidebar_label: Servidor FHIR
---

A experimentação com o FHIR pode ser feita com vários servidores de [acesso livre](../tecnologias#implementa%C3%A7%C3%A3o-do-fhir). Por outro lado, há possibilidade
de controle total em um servidor local.

## Servidor FHIR local

- Faça o download do aplicativo _Command Line Interface Tool for HAPI FHIR_. A versão 5.1.0 pode ser baixada [aqui](https://github.com/hapifhir/hapi-fhir/releases/download/v5.1.0/hapi-fhir-5.1.0-cli.zip).

- Extraia o conteúdo do arquivo .zip. Isto pode ser feito com o utilitário [jar](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/jar.html) que acompanha o JDK. Possivelmente com o comando  
  `jar xvf hapi-fhir-5.1.0-cli.zip`.

- Coloque o servidor FHIR em execução com o comando abaixo. Consulte detalhes das opções [aqui](https://hapifhir.io/hapi-fhir/docs/tools/hapi_fhir_cli.html).  
  `hapi-fhir-cli run-server -v r4`
