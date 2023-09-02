## Executar sentença em FHIRPath

- `npm install`
- `npx fhirpath -f exemplo.json -e "identifier"`

## Executar sentença em FHIRPath

- `npm install`
- `node_modules\.bin\fhirpath -f exemplo.json -e "identifier"`

## Exemplos

Informa para cada nome se contém ou não a letra ‘e’.

- name.given.select($this +  iif($this.contains('e'), '', ' does not') + ' contains letter e')

Todos os nomes oficiais devem se iniciar com letra maiúscula

- `name.where(use = 'official').given.all($this.matches('[A-Z]'))`

- `Patient.address.postalCode.matches('^\\d{4}[A-Z]{2}$')`

- `now() - 18 years > birthDate`

Quantos nomes distintos há no recurso?

- name.given.distinct().count()

Os tipos dos recursos contidos em um Bundle:

- `Bundle.entry.resource.type().name`

Os identificadores, apenas estes, de todos os recursos no Bundle em questão,
que contém element.path='Extension' e element.min = 1.

- `entry.resource.where($this.exists($this.snapshot.element.where(path='Extension' and min = 1))).id`

Os recursos contidos em um Bundle, mas apenas aqueles que possuem valor na propriedade **title**:

- `Bundle.entry.where(resource.title.empty().not()).resource`
- `Patient.managingOrganization.reference.is(FHIR.string)`

## Ferramenta online

- https://hl7.github.io/fhirpath.js/
- http://fhirpath-lab.azurewebsites.net/FhirPath/
- http://niquola.github.io/fhirpath-demo/#/
- [fhirpath-tester](https://apps.microsoft.com/store/detail/fhirpath-tester/9NXV8QDQ154V?hl=en-us&gl=us) Microsoft
- [Firely Terminal](https://fire.ly/products/firely-terminal/) também pode ser empregado, por exemplo, `fhir predicate resourceType` mas apenas retorna true or false.

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
