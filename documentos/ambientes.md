## Ambientes

O padrão FHIR, adotado pela RNDS, possui uma RESTFul API bem-definida. 
O Software de Integração a ser criado por um laboratório, portanto, fará 
uso desta API, disponibilizada em um endereço, para se integrar à RNDS.
O uso da API, contudo, depende de um _token_ de acesso, obtido em um outro
endereço. Ou seja, temos dois endereços a serem empregados por um software
de integração com a RNDS, um deles fornece o _token_ de acesso e o outro
os serviços propriamente ditos. 

A RNDS oferece dois ambientes, um de homologação e outro de produção.
Cada um deles inclui um endereço para obtenção do _token_ de acesso e
outro para os serviços propriamente ditos para troca de informações em saúde.


### Ambiente de homologação

| Ambiente      | Homologação                                                                      |
|---------------|----------------------------------------------------------------------------------|
| Autenticação  | **https<span>:</span>//ehr-auth-hmg.saude.gov.br**                                         |
| Serviços FHIR | **https<span>:</span>//ehr-services.hmg.saude.gov.br/api** |

O ambiente de homologação existe para testes e experimentações.
O ambiente de homologação é oferecido por meio de dois pontos de acesso:

- Requisição de serviços FHIR: **https<span>:</span>//ehr-services.hmg.saude.gov.br/api**. Este é o ponto de acesso que oferece acesso à
  implementação do FHIR, oferecida pela RNDS, para experimentação. Uma requisição endereçada a este ponto de acesso deve vir acompanhada do _token_ obtido por meio do outro ponto de acesso. Novamente,
  este endereço é para experimentação.

- Requisição do _token_ de acesso (segurança): **https<span>:</span>//ehr-auth-hmg.saude.gov.br**. Este é o endereço por meio do qual o _token_ para acesso à implementação de homologação do FHIR é obtido. O _token_ obtido por este ponto de acesso pode ser reutilizado em várias requisições. Enquanto estiver válido não é necessário obter um novo _token_. Desta forma, evita-se uma chamada adicional para cada acesso ao outro ponto de acesso. Apesar de ser para homologação, exige o uso do
  certificado cadastrado no Portal de Serviços, o mesmo que será
  empregado no ambiente de produção.

### Ambiente de produção

O ambiente de produção coloca à disposição os serviços que, de fato,
enviam e recuperam informações em saúde por meio da RNDS.
À semelhança do ambiente de homologação, há um endereço
para obter o _token_ e outro, distinto para cada estado, conforme detalhado abaixo, para o acesso aos serviços FHIR.

- Requisição do _token_ de acesso: **https<span>:</span>//ehr-auth.saude.gov.br**.
  Este é o endereço único para obtenção do _token_ de segurança. Esta é a única finalidade deste ponto de acesso. Convém destacar que não é necessário obter um _token_ antes de cada requisição
  de serviço. O _token_ tem uma validade de 30 minutos e, neste período,
  é esperado que seja reutilizado.

- Requisição de serviços (formato do endereco): **https://&lt;UF&gt;-ehr-services.saude.gov.br/api**. Observe que **UF** deve ser substituído pela sigla de duas letras do estado em questão. Ao contrário do ambiente de homologação, que oferece um único ponto de acesso para requisição dos serviços, há um
  endereço (ponto de acesso) para requisição por estado. O estado é definido
  pelo CNES da instituição em questão que requisita o acesso. Por exemplo,
  se o CNES de quem requisita o serviço é do Distributo Federal, então o
  endereço a ser empregado deve ser **https<span>:</span>//df-ehr-services.saude.gov.br/api**. Por outro lado, se for do estado de Goiás, então deve ser
  **https<span>:</span>//go-ehr-services.saude.gov.br/api**, e assim por diante.
