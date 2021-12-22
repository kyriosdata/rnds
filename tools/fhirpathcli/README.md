# FhirPathCli

> Aplicação para execução de sentenças em 
> [FHIRPath](http://hl7.org/fhirpath/) via linha de comandos.

Esta linguagem foi desenvolvida com foco na definição de expressões 
lógicas e consultas sobre recursos [FHIR](https://www.hl7.org/fhir/).
Sentenças em FHIRPath podem ser criadas e executadas _online_ via 
[fhirpath.js](https://hl7.github.io/fhirpath.js/).

## Implementação de referência e documentação

A implementação de referência de FHIRPath encontra-se disponível no
[GitHub](https://github.com/hapifhir/hapi-fhir). Consulte a
[documentação](https://hapifhir.io/hapi-fhir/docs/appendix/javadocs.html)
para detalhes. Faz parte da documentação as principais classes empregadas
para uso da implementação de referência:

- [Core base](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/) e
- [Model R4](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-structures-r4/)

## Análise

A entrada para o aplicativo é formada por recurso(s) FHIR e uma sentença
em FHIRPAth para ser executada. A saída é o resultado da execução da sentença 
sobre o recurso ou recursos fornecidos.

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/Zingam/Markdown-Document-UML-Use-Test/master/UML/Instance.puml)


