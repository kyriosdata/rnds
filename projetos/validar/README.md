## Validação de recursos

- O diretório **definicoes** contém as definições dos recursos e outros
  produzidos pela [RNDS](https://simplifier.net/redenacionaldedadosemsade) no serviço
  Simplifier.NET.

```shell
java -jar validador_cli.jar <recurso para validar> -ig <diretorio de definicoes> -recurse -output resultado.json
```

## Implementações

- https://github.com/hapifhir/org.hl7.fhir.core
- https://github.com/hapifhir/org.hl7.fhir.validator-wrapper
