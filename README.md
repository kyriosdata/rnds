# Experimentações tecnológicas

Observe que

- _Este **NÃO** é um portal oficial do DATASUS/MS_. 
- _Este **NÃO** é o portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br_.
- _Este portal **NÃO** está associado, não é mantido, não é vistoriado, não é acompanhado nem auditado pelo DATASUS ou pelo Ministério da Saúde_.

Considerando o que foi dito acima, este portal reúne experimentações e _links_ relevantes, dentre outros, pertinentes  às tecnologias que supostamente serão empregadas pela RNDS/DATASUS.

# Componentes

## FHIR

Os primeiros passos em FHIR podem ser orientados por [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320).

<img src="https://hapifhir.io/hapi-fhir/images/logos/raccoon-forwards.png" width="100">
Hapi FHIR é uma implementação de FHIR em Java disponível em https://hapifhir.io/hapi-fhir/. Há um servidor para testes disponível em http://hapi.fhir.org/.

# JSON e XML

O portal oficial define [JSON](https://www.json.org/json-en.html) (JavaScript Object Notation) como _um formato leve para troca de dados_. Ainda acrescenta que este formato é _fácil para seres humanos escreverem e lerem_. [XML](https://en.wikipedia.org/wiki/XML), por sua vez, apresenta como objetivos, dentre outros, a usabilidade na internet. Também é empregada em _web services_ (serviços oferecidos por meio da internet).

## FHIRPath 
Respostas para requisições via FHIR são formatadas usando JSON. Para consultar tais documentos, por exemplo, extrair uma informação dentre os dados retornados, pode-se usar FHIRPath ([github](https://github.com/HL7/fhirpath)). A [especificação](http://hl7.org/fhirpath/) encontra-se amplamente disponível, assim como a versão [detalhada](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)). Há uma implementação de FHIRPath em [javascript](https://github.com/HL7/fhirpath.js), dentre outras opções.

```shell
npm install --global fhirpath
fhirpath -f resposta.json -e 'FHIRPath expression'
```

Convém destacar que documentos JSON em geral podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). Observe que JsonPath também pode ser empregado para consultar documentos JSON retornados via FHIR, contudo, FHIRPath contém recursos específicos. 

# Especificação FHIR empregada pelo Brasil
  - https://simplifier.net/
  - Especificação FHIR para RNDS [aqui](https://simplifier.net/RNDS/~introduction)
  
# Siglas
  
  - STU é a abreviação para _Standard for Trial Use_ no contexto FHIR. 
