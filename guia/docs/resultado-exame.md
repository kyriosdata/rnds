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

O resultado de exame laboratorial, por exemplo, o resultado do exame de COVID-19, é definido por meio de um recurso [Composition](https://www.hl7.org/fhir/composition.html), que referencia um recurso [Observation](https://www.hl7.org/fhir/observation.html) que, por fim, faz uso de um tercerio recurso FHIR, o [Specimen](https://www.hl7.org/fhir/specimen.html). Todos estes três recursos são necessários.

Estes três recursos FHIR não são usados conforme definidos, mas por meio de personalizações
para atender o contexto nacional. Uma personalização é definida por um perfil (_profile_).
Respectivamente, os perfis definidos pela RNDS são [Resultado
de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial) (um recurso _Composition_), [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) (um recurso _Observation_) e, por fim, [Amostra Biológica](https://simplifier.net/RedeNacionaldeDadosemSade/BRAmostraBiologica) (um recurso _Specimen_).

Adicionalmente, um quarto recurso é necessário para compor um resultado de exame laboratorial, no caso, um recurso para reunir os outros três comentados acima. O recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html) foi definido especificamente para esta finalidade, é um contêiner no qual
são depositados recursos FHIR ou uma "sacola" de recursos FHIR.

Feitas tais considerações,
o diagrama UML abaixo esclarece que um _Bundle_ é o "envelope" no qual é depositado um resultado de exame laboratorial, que inclui um diagnóstio em laboratório clínico que, por sua vez, faz uso de uma amostra biológica.

![img](../static/img/resultado-exame.png)

Todo recurso FHIR pode ser representado em JSON. A propriedade _resourceType_
é obrigatória e identifica o tipo de recurso. Para um recurso
do tipo _Bundle_ o valor desta propriedade é "Bundle", conforme ilustrado
no "esqueleto" de JSON abaixo. Adicionalmente, a propriedade
_entry_ reúne os recursos FHIR que fazem parte do _Bundle_. Neste caso,
o [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial), o [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e
a [Amostra Biológica](https://simplifier.net/RedeNacionaldeDadosemSade/BRAmostraBiologica).

```json
{
   "resourceType":"Bundle",
   "type":"document",
   "timestamp":"2020-03-20T00:00:00-03:00",
   "meta": {
      "lastUpdated": "2020-03-20T00:00:00-03:00"
   },
   "identifier":{ ... omitido ... },
   "entry":[
      { ... Resultado de Exame Laboratorial ... },
      { ... Diagnóstico em Laboratório Clínico ... },
      { ... Amostra Biológica ... }
    ]
}
```

A propriedade _type_ indica o propósito do _Bundle_, no caso, trata-se de um documento (_document_). A propriedade _timestamp_ indica o instante em que o _Bundle_ foi criado,
provavelmente o mesmo instante da última atualização, fornecida na propriedade _lastUpdate_
de _meta_.

O "esqueleto" acima ilustra como um resultado de exame laboratorial deve ser empacotado
antes de ser enviado para a RNDS. Ou seja, é preciso produzir
um JSON completo, válido, "inflado" com os valores omitidos, para as propriedades
_identifier_ e _entry_.
