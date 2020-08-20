### Requisições

#### Obter token

```json
{
    "access_token": "longa sequencia aqui.",
    "scope": "read write",
    "token_type": "jwt",
    "expires_in": 1800000
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

#### Token empregado para a requisição expirou
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
