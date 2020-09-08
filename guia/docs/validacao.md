---
id: validacao
title: Validação de Recursos
sidebar_label: Validação
---

A validação permite identificar a conformidade
de um recurso em relação a vários itens. Consulte [Validating Resources](https://www.hl7.org/fhir/validation.html)
para detalhes.

A validação é necessária para assegurar que um resultado de exame laboratorial,
por exemplo, esteja acompanhado de todas as informações necessárias e
conforme as exigências nacionais, definidas pela RNDS.
De fato, no [Portal de Serviços](https://servicos-datasus.saude.gov.br/detalhe/UZQjoYDDFN) da RNDS,
dentre os vários artefatos disponibilizados está o [Validador local aplicativo](https://doc-0k-b0-docs.googleusercontent.com/docs/securesc/pv16ckcadsrom8ll89o65she880al4qi/je4967phlp7b1fhq5kovqf8gnaakio7m/1599249225000/10214180060604046643/00115241587149400156/19c5hNwXv8qZk8Ylq-PJAnTFPr8_d5z8n?e=download&authuser=0&nonce=m7h2r4gkh32jk&user=00115241587149400156&hash=31u37radoua6t6fot61ga1qad9qsauev).

Este validador é uma aplicação gráfica por meio do qual é possível fornecer as definições ou personalizações
de FHIR estabelecidas pela RNDS e um documento JSON, por exemplo, cuja conformidade com as definições
deve ser verificada.

Uma alternativa para validação é usar o [validador](https://github.com/hapifhir/org.hl7.fhir.core/releases/latest/download/validator_cli.jar), devidamente [documentado](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator) e que permite seu uso via linha de comandos. Alguns deles são ilustrados abaixo:

```shell
java -jar validador_cli.jar <recurso para validar> -ig <diretorio de definicoes> -recurse -output resultado.xml
```
