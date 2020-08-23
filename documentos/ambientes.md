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
O ambiente de produção coloca à disposição os serviços da RNDS
para envio/consulta de informações em saúde dos usuários do SUS.
À semelhança do ambiente de homologação, há um endereço
para obter o _token_ e outro, distinto para cada estado, conforme detalhado abaixo, para o acesso aos serviços de envio/consulta de informações.

- Requisição do _token_ de acesso: **https://ehr-auth.saude.gov.br**. 
Este é o endereço único para obtenção do _token_ de acesso aos serviços 
de envio/consulta de informações. Esta é a única finalidade deste ponto de acesso. Convém destacar que não é necessário obter um _token_ antes de cada requisição
de serviço. O _token_ tem uma validade de 30 minutos e, neste período,
é esperado que seja reutilizado.

- Requisição de serviços. Ao contrário do ambiente de homologação, que oferece um único ponto de acesso para requisição dos serviços, há um
endereço (ponto de acesso) para requisição de serviços para cada
estado da federação. O formato é **https://uf-ehr-services.saude.gov.br/api**.
