Objetivo:

> Detalhar itens de dado necessários para registrar um Resultado de Exame Laboratorial.

Resultados esperados:

- Você saberá quais são os dados necessários para montar um resultado de exame de COVID-19.
- Você saberá como estes dados devem ser fornecidos no documento JSON exigido pela RNDS.
- Você será capaz de montar um documento JSON para refletir o resultado de um dado exame.

### Resultado de Exame Laboratorial

O resultado de exame laboratorial, por exemplo, o resultado do exame de COVID-19, é definido por meio de um recurso [Composition](https://www.hl7.org/fhir/composition.html), que referencia um recurso [Observation](https://www.hl7.org/fhir/observation.html) que, por fim, faz uso de um tercerio recurso FHIR, o [Specimen](https://www.hl7.org/fhir/specimen.html). Todos estes três recursos são empregados para definir um [Resultado
de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial), conforme este perfil (_profile_) definido pela RNDS.

Reunir estes três recursos correspondentes a um resultado de exame laboratorial exige um [Bundle](https://www.hl7.org/fhir/bundle.html). O recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html) é um contêiner no qual
são depositados recursos FHIR, é uma "sacola" de recursos FHIR. De fato,
para enviar resultado de exame será feito uso de um _Bundle_.

Todo recurso FHIR é representado em JSON com estrutura similar àquela abaixo,
na qual a propriedade _resourceType_ identifica o tipo de recurso. Neste caso, um _Bundle_:

```json
{
    "resourceType" : "Bundle",
    -- outros pares (chave/valor) omitidos---
}
```
