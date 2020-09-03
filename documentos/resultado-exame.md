Objetivo:

> Detalhar itens de dado necessários para registrar um Resultado de Exame Laboratorial.

Resultados esperados:

- Você saberá quais são os dados necessários para montar um resultado de exame de COVID-19.
- Você saberá como estes dados devem ser fornecidos no documento JSON exigido pela RNDS.
- Você será capaz de montar um documento JSON para refletir o resultado de um dado exame.

### Resultado de Exame Laboratorial

O recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html) é um contêiner no qual
são depositados recursos FHIR, é uma "sacola" de recursos FHIR.

O resultado de exame laboratorial, por exemplo, o resultado do exame de COVID-19, é definido por meio de um recurso [Composition](https://www.hl7.org/fhir/composition.html), que referencia um recurso [Observation](https://www.hl7.org/fhir/observation.html) que, por fim, faz uso de um tercerio recurso FHIR, o [Specimen](https://www.hl7.org/fhir/specimen.html). Todos estes três recursos são empregados para definir um [Resultado
de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial), conforme padronizado pela RNDS.

Juntar estes três recursos correspondentes para registrar o resultado de um exame laboratorial, é uma
tarefa para um [Bundle](https://www.hl7.org/fhir/bundle.html).
