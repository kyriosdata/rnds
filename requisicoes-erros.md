### Erros

### Token empregado para a requisição expirou
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
