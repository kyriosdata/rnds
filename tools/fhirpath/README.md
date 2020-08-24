## FHIRPath

A RESTful API FHIR apresenta resultados em JSON e, para consultar tais resultados, por exemplo, extrair uma informação, pode-se usar FHIRPath ([github](https://github.com/HL7/fhirpath)).

A [definição](http://hl7.org/fhirpath/) de FHIRPath, assim como [detalhes](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)) estão amplamente disponíveis. FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).

Consultas baseadas em FHIRPath podem ser executadas por meio do portal [clinfhir](http://clinfhir.com). Adicionalmente, pode-se usar a implementação [fhirpath](https://github.com/HL7/fhirpath.js) em Javascript, dentre outras opções.

```shell
npm install --global fhirpath
fhirpath -f resposta.json -e 'FHIRPath expression'
```

Convém destacar que documentos JSON em geral podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). Observe que JsonPath também pode ser empregado para consultar documentos JSON retornados via FHIR, contudo, FHIRPath contém recursos específicos.

### Instalação

```shell
$ git clone https://github.com/kyriosdata/rnds
$ cd rnds/tools/fhirpath
$ npm install
```

### Operação trivial

```shell
$ cd rnds/tools/fhirpath
$ npm install
$ npm run fhirpath -- -f exemplo-original.json -e "entry.count()"
```
