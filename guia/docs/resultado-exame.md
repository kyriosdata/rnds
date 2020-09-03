---
id: resultado
title: Resultado de Exame Laboratorial
sidebar_label: Resultado de exame
---

Objetivo:

> Detalhar itens de dado necessários para registrar um Resultado de Exame Laboratorial.

Resultados esperados:

- Você saberá quais são os dados necessários para montar um resultado de exame de COVID-19.
- Você saberá como estes dados devem ser fornecidos no documento JSON exigido pela RNDS.
- Você será capaz de montar um documento JSON para refletir o resultado de um dado exame.

### Estrutura

O resultado de exame laboratorial, por exemplo, o resultado do exame de COVID-19, é definido por meio de um recurso [Composition](https://www.hl7.org/fhir/composition.html), que referencia um recurso [Observation](https://www.hl7.org/fhir/observation.html) que, por fim, faz uso de um tercerio recurso FHIR, o [Specimen](https://www.hl7.org/fhir/specimen.html). Todos estes três recursos são necessários, conforme o perfil (_profile_) [Resultado
de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial) definido pela RNDS.

Adicionalmente, um quarto recurso é necessário para reunir os três descritos acima. O recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html) é um contêiner no qual
são depositados recursos FHIR, é uma "sacola" de recursos FHIR. Em consequência,
um resultado de exame laboratorial pode ser ilutrado usando a UML conforme abaixo.

![img](../static/img/resultado-exame.png)

Todo recurso FHIR pode ser representado em JSON e a propriedade _resourceType_
é obrigatória. Esta propriedade identifica o tipo de recurso. Para um recurso
do tipo _Bundle_ o valor desta propriedade é "Bundle", conforme ilustrado
no "esqueleto" de um JSON abaixo. Adicionalmente, é por meio da propriedade
_entry_ que vários outros recursos FHIR podem ser fornecidos. No caso de um
Resultado de Exame Laboratorial, conforme citado acima, espera-se um recurso
_Composition_, _Observation_ e, por fim, um _Specimen_.

```json
{
   "resourceType":"Bundle",
   "meta": { ... omitido ... },
   "identifier":{ ... omitido ... },
   "type":"document",
   "timestamp":"2020-03-23T14:23:56.567-02:00",
   "entry":[
      { ... Composition ... },
      { ... Observation ... },
      { ... Specimen ... }
    ]
}
```
