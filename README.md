# Experimentações tecnológicas

Este é um portal que reúne experimentações que fazem uso de tecnologias e ferramentas referenciadas pela RNDS com foco em desenvolvedores de software. 

> - _Este **NÃO** é um portal oficial do DATASUS/MS_. 
> - _Este **NÃO** é um portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br_.
> - _Tecnologias sugeridas podem não ser usadas pela RNDS_.
> - _Uso ilustrado das tecnologias pode ser equivocado, de baixa qualidade (faça sua avaliação)_.

# Componentes

- FHIR
  - Uma visão geral pode ser obtida [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320)
  - STU é a abreviação para _Standard for Trial Use_ no contexto FHIR. 
  - Implementação [Java](https://hapifhir.io/hapi-fhir/) e servidor para [testes](http://hapi.fhir.org/).
  - FHIRPath. Requisições via FHIR retornam documentos JSON.
    - Para consultar tais documentos, por exemplo, extrair uma informação relevante em um dado contexto dentre os dados retornados, pode-se usar FHIRPath ([gramática antlr4](https://github.com/HL7/fhirpath)). A [especificação](http://hl7.org/fhirpath/) encontra-se amplamente disponível. Detalhes no [github](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).
    - Implementação de FHIRPath em [javascript](https://github.com/HL7/fhirpath.js).
  - Documentos JSON em geral podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). Observe que JsonPath também pode ser empregado para consultar documentos JSON retornados via FHIR, contudo, FHIRPath contém recursos específicos. 
  - https://simplifier.net/
  
# Elementos removidos
  
