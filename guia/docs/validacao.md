---
id: validacao
title: Validação de Recursos
sidebar_label: Validação
---

A validação permite identificar a conformidade
de um recurso em relação a vários critérios. Por exemplo,
assegurar que um resultado de exame contém todas as informações necessárias conforme definidas pela RNDS. Há vários outros cenarios, consulte [Validating Resources](https://www.hl7.org/fhir/validation.html)
para detalhes.

No [Portal de Serviços](https://servicos-datasus.saude.gov.br/detalhe/UZQjoYDDFN) da RNDS,
dentre os vários artefatos disponibilizados está o [Validador local aplicativo](https://doc-0k-b0-docs.googleusercontent.com/docs/securesc/pv16ckcadsrom8ll89o65she880al4qi/je4967phlp7b1fhq5kovqf8gnaakio7m/1599249225000/10214180060604046643/00115241587149400156/19c5hNwXv8qZk8Ylq-PJAnTFPr8_d5z8n?e=download&authuser=0&nonce=m7h2r4gkh32jk&user=00115241587149400156&hash=31u37radoua6t6fot61ga1qad9qsauev). Abaixo segue uma ilustração da execução deste aplicativo.

![image](https://user-images.githubusercontent.com/1735792/92491044-21244600-f1c8-11ea-921b-541e9b77d967.png)

Este validador é uma aplicação gráfica por meio do qual é possível fornecer as definições ou personalizações
de FHIR estabelecidas pela RNDS e um documento JSON, por exemplo, cuja conformidade com as definições
deve ser verificada.

Uma alternativa para validação é usar o [validador](https://github.com/hapifhir/org.hl7.fhir.core/releases/latest/download/validator_cli.jar), devidamente [documentado](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator) e que permite seu uso via linha de comandos. Abaixo segue o formato de um comando para validar um recurso conforme definições e depositar o resultado em um arquivo JSON.

```shell
java -jar validador_cli.jar <recurso para validar> -ig <diretorio de definicoes> -recurse -output resultado.json
```

O recurso pode ser o resultado de exame laboratorial, arquivo JSON. As definições podem ser obtidas pelo portal da RNDS ou pelo serviço Simplifier.NET (veja [detalhes](./perfis)).
No acomando acima a saída será depositada em **resultado.json**. Em tempo, a representação JSON do recurso [OperationOutcome](http://hl7.org/fhir/operationoutcome.html).

## Implementações

- https://github.com/hapifhir/org.hl7.fhir.core
