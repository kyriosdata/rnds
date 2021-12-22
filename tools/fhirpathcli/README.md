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

A implementação de referência é fornecida pela classe 
[FhirPathR4](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-structures-r4/undefined/org/hl7/fhir/r4/hapi/fluentpath/FhirPathR4.html), 
que implementa a interface [IFhirPath](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/undefined/ca/uhn/fhir/fhirpath/IFhirPath.html).

A entrada para o aplicativo é formada por recurso(s) FHIR e uma sentença
em FHIRPAth para ser executada. A saída é o resultado da execução da sentença 
sobre o recurso ou recursos fornecidos.

![analise](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/kyriosdata/rnds/master/tools/fhirpathcli/UML/analise.puml)

A execução é fornecida pelo método [evaluate](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/undefined/ca/uhn/fhir/fhirpath/IFhirPath.html#evaluate(org.hl7.fhir.instance.model.api.IBase,java.lang.String,java.lang.Class)).
O primeiro argumento é um recurso ou tipo de dado FHIR.
O segundo argumento é a sentença em FHIRPath. 
O retorno é uma [lista](https://docs.oracle.com/javase/8/docs/api/java/util/List.html?is-external=true)
cujo tipo é fornecido pelo terceiro argumento deste método.
