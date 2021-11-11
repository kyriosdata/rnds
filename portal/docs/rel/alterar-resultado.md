---
id: alterar-resultado
title: Alterar resultado de exame
sidebar_label: Alterar resultado
---

Objetivo:

> Esclarecer como substituir um resultado de exame previamente submetido.

A representação completa de um _payload_ (JSON) para
substituir um resultado previamente submetido pode ser obtido na configuração do [postman](https://documenter.getpostman.com/view/2163377/TVRd9Wad).

Resultado esperado:

- Você saberá como montar um _payload_ que contém um resultado de exame para
  substituir resultado já notificado (submetido).

### Contexto

Caso seja necessário retificar um resultado de exame laboratorial, as operações
a serem realizadas são similares àquelas utilizadas para submeter um resultado.
Contudo, há alguns detalhes a serem esclarecidos.

### Passos

- Obter identificadores. São necessários o identificador do resultado
  atribuído pelo próprio laboratório e o identificador do resultado
  atribuído pela RNDS. Este último é retornado pela requisição que
  submete o resultado.

- Montar _Bundle_ de substituição. Monte o resultado de exame laboratorial que deve substituir aquele enviado anteriormente de forma semelhante ao
  documento em [Resultado de exame](mc-rel). O identificador do novo
  _Bundle_ deve ser o mesmo atribuído pelo laboratório ao resultado de exame
  a ser substituído.

A propriedade _relatesTo_ deve ser fornecida, deve fazer parte do
[Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsaude/brresultadoexamelaboratorial-duplicate-2) para indicar que o resultado em questão substitui
outro, conforme comentado abaixo.

_relatesTo_. Esta propriedade deve ser utilizada exclusivamente para indicar que este documento substitui (_replaces_) outro documento. Seu uso, portanto, está
definido para indicar que o documento que faz uso
desta propriedade visa substituir um submetido anteriormente, conforme ilustrado abaixo, onde `{{exame-id-rnds}}` é o identificador do documento,
fornecido pela RNDS, quando submetido.

```json
"relatesTo":[
    {
        "code":"replaces",
        "targetReference":{
            "reference":"Composition/{{exame-id-rnds}}"
        }
    }
]
```

Conforme observado acima, o identificador do resultado do exame criado pelo
laboratório deve ser o mesmo daquele _Bundle_ que substitui o resultado criado
anteriormente. Adicionalmente, a propriedade _relatesTo_, conforme ilustrada
acima, faz uso do outro identificador do resultado de exame anteriormente
submetido, aquele atribuído pela RNDS.
