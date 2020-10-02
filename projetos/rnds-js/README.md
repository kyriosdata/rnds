## RNDS (Rede Nacional de Dados em Saúde)

Biblioteca de acesso à RNDS (Rede Nacional de Dados em Saúde).
Os serviços oferecidos pela RNDS estão encapsulados em funções de fácil uso. Consulte o [Guia de Integração com a RNDS](https://kyriosdata.github.io/rnds) para detalhes.

### Instalar

```shell
$ npm i rnds
```

### Configurar

Tendo em vista a sensibilidade das informações que fazem parte da configuração
necessária, todas elas são fornecidas por meio de variáveis de ambiente, conforme tabela abaixo:

| Variável                  | Conteúdo                                                                             |
| ------------------------- | ------------------------------------------------------------------------------------ |
| RNDS_AUTH                 | Endereço do serviço de autenticação (sem protocolo, sem path), por exemplo, **ehr-auth-hmg.saude.gov.br**.                                                 |
| RNDS_EHR                  | Endereço dos serviços de saúde.                                                      |
| RNDS_CERTIFICADO_ENDERECO | Endereço (web ou arquivo) do certificado digital (formato **.pfx**).                 |
| RNDS_CERTIFICADO_SENHA    | Senha do certificado digital.                                                        |
| RNDS_REQUISITANTE_CNS     | CNS do profissional de saúde em nome do qual requisições serão feitas.               |
| RNDS_REQUISITANTE_UF      | Código do estado (duas letras, por exemplo, AC, DF, GO) do estabelecimento de saúde. |

### Usar

```js
// Importar biblioteca
const rnds = require("rnds");

// Token (access token) é obtido implicitamente e reutilizado

// Exibe informações do estabelecimento de saúde (CNES fornecido)
// rnds.cnes("2337991", console.log);

// Exibe resultado para consulta ao CNPJ fornecido
// rnds.cnpj("01567601000143", console.log);

// Exibe informações sobre profissional de saúde (CNS).
// Quando CNS omitido, é usado o do requisitante definido
// por variável de ambiente.
// rnds.cns(console.log);

// Exibe informações sobre o profissional de saúde
// rnds.cpf("cpf do profissional de saúde", console.log);

// Exibe informações sobre o paciente 
// rnds.paciente("cpf do paciente", console.log);
```

### Links

- Autenticação com certificado via [Javascript](https://medium.com/@sevcsik/authentication-using-https-client-certificates-3c9d270e8326)
- Cliente/validador para js ([fhir](https://www.npmjs.com/package/fhir))
