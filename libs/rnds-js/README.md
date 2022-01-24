## RNDS (Rede Nacional de Dados em Saúde)

Biblioteca de acesso à RNDS (Rede Nacional de Dados em Saúde) que contempla o envio de resultads de exames laboratoriais (COVID-19).

Os serviços oferecidos pela RNDS estão encapsulados em funções de fácil uso que implementam um [façade](https://en.wikipedia.org/wiki/Facade_pattern) para os ambientes FHIR (homologação e produção) oferecidos pelo
Ministério da Saúde (DATASUS) para o Brasil.

Consulte o [Guia de Integração com a RNDS](https://rnds-guia.saude.gov.br/) para detalhes da integração com a RNDS.

### Instalar

```shell
$ npm i rnds
```

### Configurar

Tendo em vista a sensibilidade das informações que fazem parte da configuração
necessária, todas elas são fornecidas por meio de variáveis de ambiente, conforme tabela abaixo:

| Variável                  | Conteúdo                                                                                             |
| ------------------------- | ---------------------------------------------------------------------------------------------------- |
| RNDS_AUTH                 | Endereço do serviço de autenticação (sem protocolo, sem path).Exemplo: **ehr-auth-hmg.saude.gov.br** |
| RNDS_EHR                  | Endereço dos serviços de saúde. Exemplo: **ehr-services.hmg.saude.gov.br**                           |
| RNDS_CERTIFICADO_ENDERECO | Endereço (web ou arquivo) do certificado digital (formato **.pfx**).                                 |
| RNDS_CERTIFICADO_SENHA    | Senha do certificado digital.                                                                        |
| RNDS_REQUISITANTE_CNS     | CNS do profissional de saúde em nome do qual requisições serão feitas.                               |
| RNDS_REQUISITANTE_UF      | Código do estado (duas letras, por exemplo, AC, DF, GO) do estabelecimento de saúde.                 |

### Usar

```js
// Importar biblioteca
const RNDS = require("rnds");

// Crie uma instância com logging e segurança habilitados
// Adicionalmente, se o terceiro argumento é verdadeiro, então
// verifica se versão de cliente e servidor são compatíveis,
// bem como requisita token e o guarda em cache. Em qualquer destes
// casos, em caso de falha, exceção é gerada.
const cliente = RNDS.cliente(true, true, true);

// Funções empregads para exibição
const show = console.log;
const error = (objeto) => console.log("ERRO", objeto);

// IMPORTANTE
// Token (access token) é obtido implicitamente e reutilizado
// Todas as funções retornam o objeto Resposta, que possui três
// propriedades: code (HTTP), retorno (payload) e os headers retornados.

// Contexto de atendimento
// (deve ser fornecido o CNES, cns do profissional e do paciente)
// cliente.contextoAtendimento("c", "p", "u").then(show).catch(error);

// Exibe informações do estabelecimento de saúde (CNES fornecido)
// cliente.cnes("2337991").then(show).catch(error);

// Exibe informações sobre papéis de profissional de saúde
// cliente.lotacao("p", "cnes").then(show).catch(error);

// Exibe resultado para consulta ao CNPJ fornecido
// cliente.cnpj("01567601000143").then(show);

// Exibe informações sobre profissional de saúde (CNS).
// Quando CNS omitido, é usado o do requisitante definido
// por variável de ambiente.
// (código da resposta será 404 para CNS não encontrado)
// cliente.cns("cns").then(show);

// Exibe informações sobre o profissional de saúde
// Retorna code 200 mesmo quando não encontrado (verifique payload)
// cliente.cpf("cpf").then(show).catch(error);

// Exibe informações sobre o paciente
// cliente.paciente("cpf do paciente").then(show);

// Submete um resultado de exame depositado no arquivo "exame.json".
// (um identificador único é gerado pelo instante corrente)
// const resultado = JSON.parse(fs.readFileSync("exame.json"));
// resultado.identifier.value = new Date().toString();
// const payload = JSON.stringify(resultado);
// cliente.notificar(payload).then(show).catch(error);
```

### Alguns links pertinentes

- Autenticação com certificado via [Javascript](https://medium.com/@sevcsik/authentication-using-https-client-certificates-3c9d270e8326)
- Cliente/validador para js ([fhir](https://www.npmjs.com/package/fhir))
- [SMART on FHIR](http://docs.smarthealthit.org/client-js/)
- Interação com servidor FHIR ([fhir.js](https://github.com/FHIR/fhir.js))
