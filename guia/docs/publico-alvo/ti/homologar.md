---
id: homologar
title: Homologar
sidebar_label: Homologar
---

A integração deve produzir
evidências de que foi realizada satisfatoriamente. Um documento PDF deve ser montado e reunir as
evidências identificadas e ilustradas abaixo.
O arquivo PDF contendo as evidências é obrigatório
e deve ser submetido quando o acesso ao ambiente de produção for requisitado.

As evidências devem ser preparadas com base em um resultado de exame laboratorial, submetido de forma satisfatória, ao ambiente de homologação. Evidências:

1. Resultado de exame (COVID-19). Conteúdo do _Bundle_ correspondente a um exame laboratorial submetido com sucesso ao ambiente de homologação.
1. Foto da tela (_screenshot_) do aplicativo [validador](../../rnds/tools/validacao) contendo a
   validação com sucesso do _Bundle_ submetido.
1. Código atribuído pela RNDS ao resultado.

## Resultado de exame (COVID-19)

Um resultado de exame de COVID-19 fictício é ilustrado
abaixo e ilustra algo parecido ao que deve fazer parte do arquivo PDF. Observe que abaixo, onde se encontra uma
sequência de letras X, o valor original foi substituído.
Desta forma, mesmo um exemplo fictício não identifica
um laboratório com CNES válido, nem tampouco um
usuário e um responsável.

```json
{
  "resourceType": "Bundle",
  "id": "414357aa-af1e-4152-93df-8c2bd52fa0f2",
  "type": "document",
  "timestamp": "2020-10-02T11:57:31-03:00",
  "meta": {
    "lastUpdated": "2020-10-02T11:57:31-03:00"
  },
  "identifier": {
    "system": "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-14008",
    "value": "414357aa-af1e-4152-93df-8c2bd52fa0f2"
  },
  "entry": [
    {
      "fullUrl": "urn:uuid:transient-0",
      "resource": {
        "resourceType": "Composition",
        "meta": {
          "profile": [
            "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRResultadoExameLaboratorial-1.1"
          ]
        },
        "status": "final",
        "type": {
          "coding": [
            {
              "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoDocumento",
              "code": "REL"
            }
          ]
        },
        "subject": {
          "identifier": {
            "system": "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0",
            "value": "XXXXXXXXXXXXX"
          }
        },
        "date": "2020-09-29T10:59:53-03:00",
        "author": [
          {
            "identifier": {
              "system": "http://www.saude.gov.br/fhir/r4/StructureDefinition/BREstabelecimentoSaude-1.0",
              "value": "XXXXXXXX"
            }
          }
        ],
        "title": "Resultado de Exame Laboratorial",
        "section": [
          {
            "entry": [
              {
                "reference": "urn:uuid:transient-1"
              }
            ]
          }
        ]
      }
    },
    {
      "fullUrl": "urn:uuid:transient-1",
      "resource": {
        "resourceType": "Observation",
        "meta": {
          "profile": [
            "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRDiagnosticoLaboratorioClinico-1.0"
          ]
        },
        "status": "final",
        "category": [
          {
            "coding": [
              {
                "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRSubgrupoTabelaSUS",
                "code": "0214"
              }
            ]
          }
        ],
        "code": {
          "coding": [
            {
              "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRNomeExameLOINC",
              "code": "94507-1"
            }
          ]
        },
        "subject": {
          "identifier": {
            "system": "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0",
            "value": "XXXXXXXXXXX"
          }
        },
        "issued": "2020-03-23T10:30:12.947-02:00",
        "performer": [
          {
            "identifier": {
              "system": "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRPessoaJuridicaProfissionalLiberal-1.0",
              "value": "XXXXXXXXXXX"
            }
          }
        ],
        "valueCodeableConcept": {
          "coding": [
            {
              "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRResultadoQualitativoExame",
              "code": "1"
            }
          ]
        },
        "method": {
          "text": "Imunocromatográfico"
        },
        "referenceRange": [
          {
            "text": "(1) Detectável = presença de anticorpos; (2) Não detectável = ausência de anticorpos"
          }
        ],
        "specimen": {
          "reference": "urn:uuid:transient-2"
        }
      }
    },
    {
      "fullUrl": "urn:uuid:transient-2",
      "resource": {
        "resourceType": "Specimen",
        "meta": {
          "profile": [
            "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRAmostraBiologica-1.0"
          ]
        },
        "type": {
          "coding": [
            {
              "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoAmostraGAL",
              "code": "SGHEM"
            }
          ]
        }
      }
    }
  ]
}
```

## Foto da tela (_screenshot_) do validador

O conteúdo da seção anterior foi submetido ao validador
e o resultado é exibido abaixo. Uma imagem similar também
deve fazer parte do PDF a ser montado. Observe que não foi
identificada, nesta validação, nenhuma falha.

![evidencias-screenshot](../../../static/img/evidencias.png)

## Código atribuído pela RNDS ao resultado

Um resultado submetido de forma satisfatória recebe um
identificador gerado pela RNDS. Este identificador é
retornado por meio de dois _headers_ da resposta à submissão do resultado: (a) **content-location** e (b) **location**.

O conteúdo destes _headers_ é o mesmo. O identificador atribuído pela RNDS ao resultado segue imediatamente a última barra "/" e vai até o último caractere da resposta.

Um exemplo é fornecido abaixo, onde a parte inicial foi simplesmente omitida. Neste caso, o identificador atribuído pela RNDS é a sequência que se inicia com `eb4fc`e se estende até `r3x7`.

`.../r4/Bundle/eb4fc099-e5e2-4895-9d3b-23a6de2d7324-r3x7`

:::tip Importante
O identificador atribuído pela RNDS deve ser extraído
do conteúdo de um destes _headers_ e armazenado para
eventual uso posterior. Por exemplo, a substituição do resultado.
:::
