---
id: resultado
title: Resultado de Exame Laboratorial
sidebar_label: Resultado de exame
---

Objetivo:

> Detalhar itens de dado necessários para registrar um Resultado de Exame Laboratorial. A representação JSON de um resultado completo pode ser obtido AQUI.

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
provavelmente o mesmo instante da última atualização, fornecida na propriedade _meta.lastUpdate_.

O "esqueleto" acima ilustra como um resultado de exame laboratorial deve ser empacotado
antes de ser enviado para a RNDS. Ou seja, falta completar este "esqueleto", produzir
um JSON válido, "inflado" com os valores omitidos para as propriedades
_identifier_ (identificador do _Bundle_) e _entry_ (elementos do resultado).

### Identificador (_identifier_)

A montagem de um identificador (_identifier_) é realizada a partir de dois valores, o identificador do solicitante e o identificador do resultado, respectivamente representados abaixo por {{labID}} e {{resultadoID}}:

```json
    "identifier": {
        "system": "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-{{labID}}",
        "value": "{{resultadoID}}"
    }
```

O identificador do solicitante, representado acima por {{labID}}, é fornecido pela RNDS quando
o pedido de credenciamento do laboratório em questão é aprovado. A figura abaixo ilustra o local onde o responsável pelo laboratório pode localizar o identificador do laboratório. Convém ressaltar que não se trata do CNES do laboratório, mas de um identificador que será criado pela RNDS e atribuído ao laboratório. Tanto o número da solicitação de credenciamento quanto o identificador do solicitante, nesta figura, estão ocultados por uma questão de segurança.

![img](https://user-images.githubusercontent.com/1735792/90821002-9eb30f80-e308-11ea-8636-58645a1fa3c2.png)

O identificador do resultado de exame, por outro lado, é um identificador criado pelo laboratório para unicamente identificar o resultado em questão. Quaisquer dois resultados
produzidos pelo laboratório devem, necessariamente, possuir identificadores distintos.
O laboratório pode optar por criar identificadores sequenciais, por exemplo, "1", "2", e assim por diante. Ou ainda, "2020-09-04-0001", "2020-09-04-0002" e assim por diante, caso o
identificar inclua o dia em que é gerado, por exemplo. Também pode gerar um identificador universalmente único (_Universally Unique IDentifier_) ou [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier). Veja como podem ser gerados em [Java](https://www.baeldung.com/java-uuid) e [JavaScript](https://www.npmjs.com/package/uuid), por exemplo.

De posse tanto do identificador do solicitante, por exemplo, "99", quanto do identificador de um resultado de exame a ser enviado para a RNDS, digamos "04/09/2020-cdYQj", o trecho do JSON correspondente à propriedade _identifier_, a ser enviado para a RNDS, é

```json
    "identifier": {
        "system": "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-99",
        "value": "04/09/2020-cdYQj"
    }
```

Em consequência, o "esqueleto" JSON pode ser reescrito, considerando o detalhamento do _identifier_, conforme abaixo:

```json
{
   "resourceType":"Bundle",
   "type":"document",
   "timestamp":"2020-03-20T00:00:00-03:00",
   "meta": {
      "lastUpdated": "2020-03-20T00:00:00-03:00"
   },
   "identifier": {
      "system": "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-99",
      "value": "04/09/2020-cdYQj"
   },
   "entry":[
      { ... Resultado de Exame Laboratorial ... },
      { ... Diagnóstico em Laboratório Clínico ... },
      { ... Amostra Biológica ... }
    ]
}
```

### Elementos do resultado

Um _Bundle_ é empregado para reunir recursos FHIR. A propriedade _entry_ é o local onde os recursos devem ser fornecidos (observe que é um _array_). No caso em questão, tal _array_, propriedade _entry_, deverá possuir três entradas:

```json
   "entry":[
      { ... Resultado de Exame Laboratorial ... },
      { ... Diagnóstico em Laboratório Clínico ... },
      { ... Amostra Biológica ... }
    ]
```

Estas três entradas, respectivamente, referem-se aos seguintes perfis, personalizações definidas pela RNDS: [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial), [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e
[Amostra Biológica](https://simplifier.net/RedeNacionaldeDadosemSade/BRAmostraBiologica).

Observe que estes recursos são fornecidos em entradas próprias da propriedade _entry_ e, consequentemente, é necessário que a amostra biológica empregada seja referenciada pelo diagnóstico que, por sua vez, deve ser referenciada pelo resultado de exame laboratorial. Estas referências são comuns e amplamente empregadas quando se uso o padrão FHIR.

### Referências entre recursos

O FHIR faz uso extensivo do conceito de [referência](https://www.hl7.org/fhir/references.html). Ou seja, muitos dos elementos que compõem um recurso são referências para outros recursos. Dito de outra forma,
em vez do diagnóstico em laboratório clínico (_Observation_) ser fornecido "embutido" em um resultado de exame laboratorial (_Composition_), este último recurso referencia (aponta para) o recurso que é o diagnóstico (_Observation_).

Em geral, em um conjunto de recursos FHIR há referências entre os recursos, formando uma rede de informação em saúde. Conforme ilustrado abaixo, a rede para um resultado de exame laboratórial é formada por três recursos, entre os quais há duas referências. O recurso Resultado possui uma referência para Diagnóstico que, por sua vez, referencia o recurso Amostra.

![img](../static/img/referencias.png)

Convém reiterar por meio de mais um exemplo. A amostra biológica (_Specimen_) é parte de um diagnóstico (_Observation_), contudo, esta parte (_Specimen_) é fornecida em sua própria entrada em _entry_, e referenciada pelo diagnóstico (_Observation_) que a utiliza.

O trecho de JSON abaixo, ressaltando apenas a propriedade _entry_ de um _Bundle_, é fornecido para a localização e compreensão do emprego de referências entre recursos FHIR. Cada entrada do _array_ possui o seu endereço ou identificador único na propriedade _fullUrl_ e, naturalmente, o recurso em questão (_resource_). Cada recurso possui o seu próprio _resourceType_ e, portanto, sabe-se que os três recursos são um _Composition_, um _Observation_ e, por fim, um _Specimen_. Ainda observe como é estabelecida a referência entre recursos.
Na segunda entrada do _array_, _Observation_, há uma referência (_reference_) para a terceira entrada cujo identificador é "urn:uuid:transient-2". De forma análoga, na primeira entrada há uma referência (_reference_) cujo valor é "urn:uuid:transient-1", indicando o recurso referenciado, o _Observation_.

```json
"entry": [
   {
      "fullUrl": "urn:uuid:transient-0",
      "resource": {
        "resourceType": "Composition",
        ... "reference": "urn:uuid:transient-1" ...
   },
   {
      "fullUrl": "urn:uuid:transient-1",
      "resource": {
        "resourceType": "Observation",
         ... "reference": "urn:uuid:transient-2" ...
      }
   },
   {
      "fullUrl": "urn:uuid:transient-2",
      "resource": {
        "resourceType": "Specimen"
      }
   }
]
```

Tendo em vista que os recursos que definem um resultado de exame laboratorial foram identificados ([Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial), [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e
[Amostra Biológica](https://simplifier.net/RedeNacionaldeDadosemSade/BRAmostraBiologica)), e que cada um deles é fornecido em entrada própria na propriedade _entry_ (conforme ilustrado acima), e que a ligação entre eles é estabelecida por meio de referências, é preciso prosseguir e preencher cada um destes recursos.
