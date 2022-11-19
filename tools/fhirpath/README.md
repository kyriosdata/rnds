## Exemplos

Informa para cada nome se contém ou não a letra ‘e’.
- name.given.select($this +  iif($this.contains('e'), '', ' does not') + ' contains letter e')

Quantos nomes distintos há no recurso?
- name.given.distinct().count()

## Ferramenta online

- https://hl7.github.io/fhirpath.js/
- http://niquola.github.io/fhirpath-demo/#/
- http://fhirpath-lab.azurewebsites.net/FhirPath/
- [fhirpath-tester](https://apps.microsoft.com/store/detail/fhirpath-tester/9NXV8QDQ154V?hl=en-us&gl=us) Microsoft


## FHIRPath

Documentos JSON, por exemplo, aqueles retornados por um servidor FHIR, em geral, podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). FHIRPath é similar a JsonPath, contudo, contém funções específicas.

FHIRPath está definido [aqui](http://hl7.org/fhirpath/). Consulte [detalhes](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).

Consultas baseadas em FHIRPath podem ser executadas por meio do portal [clinfhir](http://clinfhir.com). Adicionalmente, pode-se usar a implementação [fhirpath](https://github.com/HL7/fhirpath.js) em Javascript, dentre outras opções.
Esta implementação é empregada nos exemplos fornecidos abaixo.

### Instalação

Projeto que usa a implementação [fhirpath](https://github.com/HL7/fhirpath.js) em Javascript.

```shell
$ git clone https://github.com/kyriosdata/rnds
$ cd rnds/tools/fhirpath
$ npm install
```

### Operação trivial

Contando quantas entradas possuem no documento **exemplo.json**.

- Qual o tipo do recurso?

```shell
$ yarn fhirpath -- -f exemplo.json -e "resourceType"
```

- Quando o _Bundle_ foi atualizado pela última vez?

```shell
$ yarn fhirpath -- -f exemplo.json -e "meta.lastUpdated"
```

- Qual o identificador local (definido pelo laboratório) do _Bundle_?

```shell
$ yarn fhirpath -- -f exemplo.json -e "identifier.value"
```

- Quantos recursos estão reunidos no _Bundle_?

```shell
$ npm run fhirpath -- -f exemplo.json -e "entry.count()"
```

- Quais os tipos dos recursos reunidos no _Bundle_?

```shell
$ yarn fhirpath -- -f exemplo.json -e "entry.resource.resourceType"
```
