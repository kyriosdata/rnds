# Experimentações tecnológicas

Observe que

- _Este **NÃO** é um portal oficial do DATASUS/MS_.
- _Este **NÃO** é o portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br_.
- _Este portal **NÃO** está associado, não é mantido, não é vistoriado, não é acompanhado nem auditado pelo DATASUS ou pelo Ministério da Saúde_.
- _Este portal **NÃO** contém nenhuma informação privilegiada ou algo similar, ao contrário, tudo o que aqui está registrado pode ser encontrado na internet, sem restrição de acesso._

Considerando o que foi dito acima, este portal reúne experimentações e _links_ relevantes, dentre outros, pertinentes às tecnologias que supostamente serão empregadas pela RNDS/DATASUS.

# Tecnologias

## FHIR

[<img src="https://www.hl7.org/fhir/assets/images/fhir-logo-www.png" width="100">](https://www.hl7.org/fhir/)

Conforme o portal https://hl7.org/fhir/, FHIR _é um padrão para a troca de dados em saúde_, o acrônimo vem de _Fast Healthcare Interoperability Resources_. Conforme o próprio nome, _resource_ ou recurso é o elemento básico empregado para a troca de dados. Em tempo, um recurso representa algum tipo de entidade do cuidado em saúde.

No momento em que esta página é escrita estão definidos 145 tipos distintos de [recursos](https://www.hl7.org/fhir/resourcelist.html).

Quando se usa o FHIR para troca de dados, os dados são acomodados nestes blocos básicos (recursos) e empacotados em XML, JSON ou RDF antes de serem transferidos.

> Os exemplos neste portal fazem uso de JSON.

## Servidores disponíveis para experimentação

Há [vários](https://wiki.hl7.org/Publicly_Available_FHIR_Servers_for_testing)servidores que podem ser empregados livremente para experimentar o FHIR, noutras palavras, enviar requisições e consultar os resultados com bases criadas especificamente para testes.

## Requisições (como submeter)

A ferramenta [Postman](https://www.getpostman.com/downloads/) é amplamente empregada para submeter requisições HTTP.

Os primeiros passos em FHIR podem ser orientados por [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320).

[<img src="https://hapifhir.io/hapi-fhir/images/logos/raccoon-forwards.png" width="100">](https://hapifhir.io)

Hapi FHIR é uma implementação de FHIR em Java disponível em https://hapifhir.io/hapi-fhir/. Há um servidor para testes disponível em http://hapi.fhir.org/.

# JSON e XML

O portal oficial define [JSON](https://www.json.org/json-en.html) (JavaScript Object Notation) como _um formato leve para troca de dados_. Ainda acrescenta que este formato é _fácil para seres humanos escreverem e lerem_.

[XML](https://en.wikipedia.org/wiki/XML), à semelhança de JSON, é comumente empregado em _web services_ (serviços oferecidos por meio da internet).

## FHIRPath

Respostas para requisições via FHIR são formatadas usando JSON. Para consultar tais documentos, por exemplo, extrair uma informação dentre os dados retornados, pode-se usar FHIRPath ([github](https://github.com/HL7/fhirpath)). A [especificação](http://hl7.org/fhirpath/) encontra-se amplamente disponível, assim como a versão [detalhada](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).

Consultas baseadas em FHIRPath podem ser executadas por meio do portal [clinfhir](http://clinfhir.com). Adicionalmente, pode-se usar a implementação em [javascript](https://github.com/HL7/fhirpath.js), dentre outras opções.

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
