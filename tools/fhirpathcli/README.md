# FhirPathCli

> Aplicação para execução de sentenças em 
> [FHIRPath](http://hl7.org/fhirpath/) via linha de comandos.

Esta linguagem foi desenvolvida com foco na definição de expressões 
lógicas e consultas sobre recursos [FHIR](https://www.hl7.org/fhir/).
Sentenças em FHIRPath podem ser criadas e executadas _online_ via 
[fhirpath.js](https://hl7.github.io/fhirpath.js/).

## Análise

### Implementação de referência

A implementação de referência de FHIRPath encontra-se disponível no
[GitHub](https://github.com/hapifhir/hapi-fhir). Consulte a
[documentação](https://hapifhir.io/hapi-fhir/docs/appendix/javadocs.html)
para detalhes. Parte desta implementação inclui os projetos

- [Model R4](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-structures-r4/) e
- [Core base](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/).

Nestes projetos estão disponíveis, respectivamente, a classe 
[FhirPathR4](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-structures-r4/undefined/org/hl7/fhir/r4/hapi/fluentpath/FhirPathR4.html), 
e a interface [IFhirPath](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/undefined/ca/uhn/fhir/fhirpath/IFhirPath.html).
A classe implementa a interface. 

A execução de sentença em FHIRPath é fornecida pelo método [evaluate](https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-base/undefined/ca/uhn/fhir/fhirpath/IFhirPath.html#evaluate(org.hl7.fhir.instance.model.api.IBase,java.lang.String,java.lang.Class)).
O primeiro argumento deste método é um recurso ou tipo de dados FHIR.
O segundo argumento é a sentença em FHIRPath (sequência de caracteres).
O retorno é uma [lista](https://docs.oracle.com/javase/8/docs/api/java/util/List.html?is-external=true)
cujo tipo é fornecido pelo terceiro argumento deste método.

### Entrada/saída

A entrada para o aplicativo é formada por recurso(s) FHIR e uma sentença
em FHIRPAth para ser executada. A saída é o resultado da execução da sentença 
sobre o recurso ou recursos fornecidos.

![modelo](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/kyriosdata/rnds/master/tools/fhirpathcli/UML/analise.puml)


