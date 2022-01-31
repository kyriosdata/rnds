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

const rnds = await RNDS.cliente(true, true, true);

rnds.checkVersion()
    .then(c => mostre("FHIR VERSION", c ? "ok" : "erro"))
    .catch(() => mostre("erro ao verificar versão..."));
```

Ou ainda, 

```javascript
const CNPJ = "01567601000143";
const CNES = "2337991";
const CNS = "980016287385192";

try {
    const rnds = await RNDS.cliente(false, true, true);

    await status(rnds.capability(), "CapabilityStatement");

    console.log(await rnds.checkVersion() ? "ok" : "erro", "FHIR VERSION");

    await status(rnds.atendimento(CNES, CNS, CNS), "contextoAtendimento");
    await status(rnds.cnes(CNES), "CNES");

    // Obtém CPF para uso posterior (informação sensível não registrada)
    const resposta = await rnds.cns(CNS)
    console.log(resposta.code === 200 ? "ok" : "erro", "CNS");
    const idt = JSON.parse(resposta.retorno).identifier;
    const idx = idt.findIndex(i => i.system.endsWith("/cpf"));
    const codigoCPF = idt[idx].value;

    await status(rnds.cpf(codigoCPF), "CPF");
    await status(rnds.cnpj(CNPJ), "CNPJ");
    await status(rnds.lotacoes(CNS, CNES), "CNS/CNES");
    await status(rnds.lotacaoPorCns(CNS), "CNS (lotações)");
    await status(rnds.lotacaoPorCnes(CNES), "CNES (lotações)");
    await status(rnds.lotacaoCnsEmCnes(CNS, CNES), "CNS/CNES");
    await status(rnds.pacientePorCns(CNS), "CNS (Bundle)");
    await status(rnds.pacientePorCpf(codigoCPF), "CPF (Patient)");
    await status(rnds.paciente(CNS), "CNS (paciente - Patient)");
} catch (erro) {
    console.log(erro);
}
```

### Alguns links pertinentes

- Autenticação com certificado via [Javascript](https://medium.com/@sevcsik/authentication-using-https-client-certificates-3c9d270e8326)
- Cliente/validador para js ([fhir](https://www.npmjs.com/package/fhir))
- [SMART on FHIR](http://docs.smarthealthit.org/client-js/)
- Interação com servidor FHIR ([fhir.js](https://github.com/FHIR/fhir.js))
