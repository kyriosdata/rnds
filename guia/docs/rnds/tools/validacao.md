---
id: validacao
title: Validação de Recursos
sidebar_label: Validação
---

Validar um recurso é assegurar que um resultado de exame, por exemplo, contém todas as informações exigidas conforme definidas pela RNDS. A [validação de recursos](https://www.hl7.org/fhir/validation.html) pode considerar vários aspectos.

A validação é particularmente útil durante o desenvolvimento ou outro cenário no qual
se deseja conferir um recurso a ser enviado ou recebido.

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

O recurso pode ser o resultado de exame laboratorial, arquivo JSON. As [definições](../perfis) estão amplamente disponíveis.
No acomando acima a saída será depositada em **resultado.json**. Em tempo, a representação JSON do recurso [OperationOutcome](http://hl7.org/fhir/operationoutcome.html).

## Implementações

- https://github.com/hapifhir/org.hl7.fhir.core
