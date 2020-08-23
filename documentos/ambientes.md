## Ambientes

A RNDS oferece dois ambientes, um de homologação e outro de produção.
Estes ambientes implementam o FHIR e oferecem a um software cliente
o meio para enviar e receber informações em saúde de usuários do SUS.
 
### Ambiente de homologação
O ambiente de homologação é oferecido para testes e experimentações.
É oferecido por meio de dois pontos de acesso:

- Requisição de serviços: **https<span>:</span>//ehr-services.hmg.saude.gov.br/api**. Este é o ponto de acesso que, de fato, oferece acesso à
implementação do FHIR oferecida pela RNDS. Uma requisição endereçada 
a este ponto de acesso deve vir acompanhado do _token_ obtido por meio
do outro ponto de acesso. 

- Requisição do _token_ de acesso: **https<span>:</span>//ehr-auth-hmg.saude.gov.br**. Este ponto de acesso por meio do qual o _token_ para acesso
à implementação do FHIR é obtido. O _token_ obtido por este ponto
de acesso pode ser reutilizado em várias requisições. Enquanto estiver
válido, o que é definido por um período de tempo (30 minutos), não é necessário obter um novo _token_.  Desta forma, evita-se uma chamada adicional para cada acesso ao outro ponto de acesso.

### Ambiente de produção
O ambiente de produção disponibiliza o catálogo de serviços da RNDS
para envio e consulta a informações em saúde dos usuários do SUS.
À semelhança do ambiente de homologação são oferecidos dois 
pontos de acesso. Por outro lado, um dos pontos de acesso depende
da localização do software cliente (há um ponto de acesso para
cada estado).
