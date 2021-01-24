---
id: erros
title: Erros
sidebar_label: Erros
---

### Requisições

#### Obter token

```json
{
  "access_token": "longa sequencia aqui. Este token será necessário, o valor deste campo, em todas as demais requisições. Ele será fornecido por meio do header X-Authorization-Server, conforme detalhado abaixo.",
  "scope": "read write",
  "token_type": "jwt",
  "expires_in": 1800000
}
```

O valor da propriedade **access_token** será empregado em todas as demais requisições. As requisições fazem uso deste valor por
meio do header **X-Authorization-Server**. Em particular, o valor deste _header_ deve ser fornecido no seguinte formato:

```
X-Authorization-Server: Bearer <valor-de-access-token-aqui>
```

Convém observar que esta não é a única informação de segurança exigida, outro _header_, **Authorization**, cujo valor deve ser o CNS do requisitante, também deve ser fornecido nas requisições aos serviços _EHR_.

#### Código inválido CNS

Na consulta por profissional via CNS, se o código CNS é inválido, por exemplo, então tem-se o resultado abaixo:

```json
{
  "resourceType": "OperationOutcome",
  "issue": [
    {
      "severity": "error",
      "code": "processing",
      "diagnostics": "Resource Practitioner/<codivo-invalido> is not known"
    }
  ]
}
```

#### Submeter bundle com identificador inválido do solicitante

O identificador do solicitante, após devidamente cadastrado, pode ser recuperado conforme ilustrado abaixo, pelo Portal de Serviços:

![image](https://user-images.githubusercontent.com/1735792/90821002-9eb30f80-e308-11ea-8636-58645a1fa3c2.png)

O identificador que acima segue ocultado, deve ser fornecido no lugar do texto "qualquer", no trecho da requisição (bundle),
conforme ilustrado abaixo:

```json
    "identifier": {
        "system": "http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-qualquer",
        "value": "valor-unico-do-bundle-para-o-lab"
    },
```

Se o valor "qualquer" não é substituído pelo identificador do solicitante, a resposta será aquela abaixo:

```json
{
  "resourceType": "OperationOutcome",
  "issue": [
    {
      "severity": "error",
      "code": "security",
      "diagnostics": "(EHR-ERR881) Você não possui autorização para utilizar esse sistema de origem: http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-qualquer"
    }
  ]
}
```

#### (422) Submeter recurso com número que não é único para o laboratório

Este identificador é aquele fornecido por meio da propriedade **identifier.value** do _bundle_.

```json
{
  "resourceType": "OperationOutcome",
  "issue": [
    {
      "severity": "error",
      "code": "processing",
      "diagnostics": "(EHR-ERR866) O identifier informado já foi utilizado para cadastrar outro documento e não pode ser repetido."
    }
  ]
}
```

### Erros

#### Requisições dependem de valores de entrada

A consulta a um estabelecimento de saúde via seu CNES exige que o código correspondente seja fornecido na URL, por exemplo,
`https://ehr-services.hmg.saude.gov.br/api;fhir/r4/Organization/2337991`. Se o código, neste exemplo, `2337991` ou outro não
é fornecido, então você receberá como resposta o conteúdo abaixo:

```json
{
  "resourceType": "OperationOutcome",
  "issue": [
    {
      "severity": "error",
      "code": "processing",
      "diagnostics": "Invalid request: The FHIR endpoint on this server does not know how to handle GET operation[Organization/] with parameters [[]]"
    }
  ]
}
```

#### Requisição com consistência verificada

A consulta por CNES retorna a resposta abaixo se o profissional de saúde em nome do qual a
requisição é feita (valor omitido na resposta abaixo) não possui vínculo com a credencial também omitida.

```json
{
  "resourceType": "OperationOutcome",
  "issue": [
    {
      "severity": "error",
      "code": "security",
      "diagnostics": "(EHR-ERR906) Profissional CNS <numero aqui> não autorizado, pois não possui vínculo CBO autorizado em nenhum dos estabelecimentos autorizados para a credencial <numero aqui>."
    }
  ]
}
```

#### (401) Token empregado para a requisição expirou

Lembre-se de que o _token_, quando obtido, tem uma validade de 30 minutos. Após estes 30 minutos
qualquer requisição que o utilize irá retornar algo similar ao conteúdo abaixo:

```js
{
    "resourceType": "OperationOutcome",
    "issue": [
        {
            "severity": "error",
            "code": "security",
            "diagnostics": "(EHR-ERR882) O token de certificado usado para autorizar o acesso não é válido. JWT expired at 2020-08-19T23:54:28Z. Current time: 2020-08-20T11:00:27Z, a difference of 39959356 milliseconds.  Allowed clock skew: 0 milliseconds."
        }
    ]
}
```

#### (422) Valor de "status" diferente de "final"

Se tentar submeter um laudo, cujo **status** é diferente de **final**, conforme ilustrado abaixo

![image](https://user-images.githubusercontent.com/1735792/91478272-f5bf6400-e875-11ea-98ab-ac7961384967.png)

e compatível com o perfil [Estado da Observação](https://simplifier.net/redenacionaldedadosemsaude/brestadoobservacao-1.0), terá como resposta

```js
{
    "resourceType": "OperationOutcome",
    "issue": [
        {
            "severity": "error",
            "code": "processing",
            "diagnostics": "(EHR-ERR924) Ao enviar um documento é obrigatório utilizar o status: final"
        }
    ]
}
```
