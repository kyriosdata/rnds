---
id: validacao
title: Validação de Recursos
sidebar_label: Validação
---

A validação visa assegurar que recursos criados e/ou recebidos são válidos. Vários [critérios](https://www.hl7.org/fhir/validation.html) podem ser considerados na validação de recursos FHIR.

Além das exigências predefinidas pelo FHIR, também é possível validar a conformidade com perfis, que introduzem restrições adicionais visando atender um uso específico. A RNDS, por exemplo, estabelece dezenas de perfis (profiles), ValueSets, CodeSystems e Extensions, que são elementos por meio dos quais especificidades, no caso, aquelas do Brasil, podem ser contempladas.

## Exemplo

Abaixo segue uma Amostra Biológica em conformidade com as restrições nacionais.

```json
{
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
```

Um laboratório, ao construir uma Amostra Biológica, deve observar que o Brasil restringe o uso de vários elementos do recurso Specimen, dentre eles, _receivedTime_, utilizado para registrar o instante em que a amostra foi recebida para processamento e outros, além de _status_. Isto significa que uma Amostra Biológica, em conformidade com as especificidades nacionais, não deve conter o instante em que amostra foi recebida, assim como também não deve conter o elemento _status_, conforme a Amostra Biológica válida fornecida acima.

Uma Amostra Biológica com o elemento _status_, por exemplo, conforme ilustrada abaixo é, portanto, uma Amostra Biológica que não está em conformidade com as especificidades nacionais.

```json
{
  "resourceType": "Specimen",
  "meta": {
    "profile": [
      "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRAmostraBiologica-1.0"
    ]
  },
  "status": "available",
  "type": {
    "coding": [
      {
        "system": "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoAmostraGAL",
        "code": "SGHEM"
      }
    ]
  }
}
```

Por fim, ao se tentar validar o recurso fornecido acima, o processo deve falhar, dado que não está em conformidade com o perfil indicado. De fato,
a resposta

Instance count for 'Specimen.status' is 1, which is not within the specified cardinality of 0..0

indica exatamente a falha, a introdução de um elemento não permitido
nas definições nacionais. Esta resposta foi produzida por um validador
fornecido pelo Simplifier.Net, comentado abaixo, o mesmo serviço utilizado pela RNDS para divulgação das especificidades nacionais para o padrão FHIR. As definições encontram-se disponíveis no endereço

https://simplifier.net/redenacionaldedadosemsaude

Naturalmente, todas as definições nacionais devem estar disponíveis e serem conhecidas pelos integradores, até para que possam ser observadas e as validações realizadas.

## Como validar um recurso?

É possível validar manualmente um recurso, sem apoio de um software. Contudo, não é uma alternativa prática. Felizmente há várias opções, que podem ser utilizadas conforme a necessidade que se tem.

### Interface gráfica

A RNDS disponibiliza um [validador](https://doc-0k-b0-docs.googleusercontent.com/docs/securesc/pv16ckcadsrom8ll89o65she880al4qi/je4967phlp7b1fhq5kovqf8gnaakio7m/1599249225000/10214180060604046643/00115241587149400156/19c5hNwXv8qZk8Ylq-PJAnTFPr8_d5z8n?e=download&authuser=0&nonce=m7h2r4gkh32jk&user=00115241587149400156&hash=31u37radoua6t6fot61ga1qad9qsauev) gráfico local. Abaixo segue uma ilustração da execução deste aplicativo.

![image](https://user-images.githubusercontent.com/1735792/92491044-21244600-f1c8-11ea-921b-541e9b77d967.png)

Este validador é uma aplicação gráfica por meio do qual é possível fornecer as definições ou personalizações
de FHIR estabelecidas pela RNDS e um documento JSON, por exemplo, cuja conformidade com as definições
deve ser verificada.

### Linha de comandos

Uma alternativa para validação é usar o aplicativo de linha de comandos [validator](https://github.com/hapifhir/org.hl7.fhir.core/), devidamente [documentado](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator) e que permite seu uso via linha de comandos. Abaixo segue o formato de um comando para validar um recurso conforme definições e depositar o resultado em um arquivo JSON.

```shell
java -jar validador_cli.jar <recurso para validar> -ig <diretorio de definicoes> -recurse -output resultado.json
```

O recurso pode ser o resultado de exame laboratorial, arquivo JSON. As [definições](../definicoes) estão amplamente disponíveis.
No acomando acima a saída será depositada em **resultado.json**. Em tempo, a representação JSON do recurso [OperationOutcome](http://hl7.org/fhir/operationoutcome.html).

## Implementações

- https://github.com/hapifhir/org.hl7.fhir.core
