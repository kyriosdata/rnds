# Experimentações tecnológicas

Este é um portal que reúne experimentações que fazem uso de tecnologias e ferramentas referenciadas pela RNDS com foco em desenvolvedores de software. 

> - _Este **NÃO** é um portal oficial do DATASUS/MS_. 
> - _Este **NÃO** é um portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br_.
> - _Tecnologias sugeridas podem não ser usadas pela RNDS_.
> - _Uso ilustrado das tecnologias pode ser equivocado, de baixa qualidade (faça sua avaliação)_.

# Componentes

## FHIR

Os primeiros passos em FHIR podem ser orientados por [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320).

<img src="https://hapifhir.io/hapi-fhir/images/logos/raccoon-forwards.png" width="100">
Hapi FHIR é uma implementação de FHIR em Java disponível em https://hapifhir.io/hapi-fhir/. Há um servidor para testes disponível em http://hapi.fhir.org/.

## JSON 
Respostas para requisições via FHIR são formatadas usando JSON. Para consultar tais documentos, por exemplo, extrair uma informação dentre os dados retornados, pode-se usar FHIRPath ([github](https://github.com/HL7/fhirpath)). A [especificação](http://hl7.org/fhirpath/) encontra-se amplamente disponível, assim como a versão [detalhada](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)). Há uma implementação de FHIRPath em [javascript](https://github.com/HL7/fhirpath.js), dentre outras opções.

Convém destacar que documentos JSON em geral podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). Observe que JsonPath também pode ser empregado para consultar documentos JSON retornados via FHIR, contudo, FHIRPath contém recursos específicos. 

# Outros
  - https://simplifier.net/
  
# Siglas
  
  - STU é a abreviação para _Standard for Trial Use_ no contexto FHIR. 
