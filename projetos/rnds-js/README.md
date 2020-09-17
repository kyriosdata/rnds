## RNDS (Brasil)

Biblioteca de acesso à RNDS (Rede Nacional de Dados em Saúde).
Os serviços oferecidos pela RNDS estão encapsulados em funções de conveniência,
de fácil uso.

### Preparando

Tendo em vista a sensibilidade das informações que fazem parte da configuração
necessária, todas elas são fornecidas por meio de variáveis de ambiente, conforme tabela abaixo:

| Variável                  | Conteúdo                                                                             |
| ------------------------- | ------------------------------------------------------------------------------------ |
| RNDS_AUTH                 | Endereço do serviço de autenticação.                                                 |
| RNDS_EHR                  | Endereço dos serviços de saúde.                                                      |
| RNDS_CERTIFICADO_ENDERECO | Endereço (web ou arquivo) do certificado digital.                                    |
| RNDS_CERTIFICADO_SENHA    | Senha do certificado digital.                                                        |
| RNDS_REQUISITANTE_CNS     | CNS do profissional de saúde em nome do qual requisições serão feitas.               |
| RNDS_REQUISITANTE_UF      | Código do estado (duas letras, por exemplo, AC, DF, GO) do estabelecimento de saúde. |

### Executando
