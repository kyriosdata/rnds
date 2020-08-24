## FHIRPath

Documentos JSON, por exemplo, aqueles retornados por um servidor FHIR, em geral, podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). FHIRPath é similar a JsonPath, contudo, contém funções específicas.

FHIRPath está definido [aqui](http://hl7.org/fhirpath/). Consulte [detalhes](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).

Consultas baseadas em FHIRPath podem ser executadas por meio do portal [clinfhir](http://clinfhir.com). Adicionalmente, pode-se usar a implementação [fhirpath](https://github.com/HL7/fhirpath.js) em Javascript, dentre outras opções.
Esta implementação é empregada nos exemplos fornecidos abaixo.

### Instalação

```shell
$ git clone https://github.com/kyriosdata/rnds
$ cd rnds/tools/fhirpath
$ npm install
```

### Operação trivial

Contando quantas entradas possuem no documento **exemplo.json**.

```shell
$ npm run fhirpath -- -f exemplo-original.json -e "entry.count()"
```
