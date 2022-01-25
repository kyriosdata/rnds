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
import RNDS from "./rnds.js";

process.on('uncaughtException', function(err) {
    console.log("\nA exceção abaixo ocorreu e não foi tratada...\n");
    console.log(err);
})

const rnds = await RNDS.cliente(true, true, true);

rnds.checkVersion()
    .then(c => console.log("FHIR VERSION", c ? "ok" : "erro"))
    .catch(() => console.log("erro ao verificar versão..."));

rnds.cnes("2337991")
    .then(cnes  => console.log("CNES", cnes.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNES"));

rnds.cns("980016287385192")
    .then(cns  => console.log("CNS", cns.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNS"));

// Comentado para evitar divulgação de um CPF
// rnds.cpf("<cpf aqui>")
//     .then(cpf  => console.log("CPF", cpf.code === 200 ? "ok" : "erro"))
//     .catch(() => console.log("erro ao obter CNES"));

rnds.cnpj("01567601000143")
    .then(cnpj  => console.log("CNPJ", cnpj.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNPJ"));
```

### Alguns links pertinentes

- Autenticação com certificado via [Javascript](https://medium.com/@sevcsik/authentication-using-https-client-certificates-3c9d270e8326)
- Cliente/validador para js ([fhir](https://www.npmjs.com/package/fhir))
- [SMART on FHIR](http://docs.smarthealthit.org/client-js/)
- Interação com servidor FHIR ([fhir.js](https://github.com/FHIR/fhir.js))
