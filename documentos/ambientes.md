## Ambientes

O padrão FHIR, adotado pela RNDS, possui uma RESTFul API bem-definida. 
O Software de Integração a ser criado por um laboratório, portanto, fará 
uso desta API, disponibilizada em um endereço, para se integrar à RNDS.

O uso da API, contudo, depende de um _token_ de acesso, obtido em um outro
endereço. Este _token_ é parte da segurança, ele autentica quem está 
realizando a requisição. A obtenção deste _token_ exige a disponibilidade 
do Certificado Digital do laboratório em questão, ou seja, um certificado
ICP-Brasil (e-CPF ou e-CNPJ).

Adicionalmente, a RNDS oferece dois ambientes, um de homologação e outro de produção.
Cada um deles inclui um endereço para obtenção do _token_ de acesso e
outro para os serviços propriamente ditos para troca de informações em saúde.

### Ambiente de homologação (endereços)

O ambiente de homologação existe para testes e experimentações.

| Função       | Endereço                                                                         |
|--------------|----------------------------------------------------------------------------------|
| Autenticação | https<span>:</span>//ehr-auth-hmg.saude.gov.br/api/token                         |
| Serviços     | https<span>:</span>//ehr-services.hmg.saude.gov.br/api |


### Ambiente de produção (endereços)

O ambiente de produção coloca à disposição os serviços que, de fato,
enviam e recuperam informações em saúde "reais".

| Função       | Endereço                                                                         |
|--------------|----------------------------------------------------------------------------------|
| Autenticação | https<span>:</span>//ehr-auth.saude.gov.br/api/token                         |
| Serviços (endereço por unidade da federação)     | https<span>:</span>//&lt;UF&gt;ehr-services.saude.gov.br/api |

Observe que no ambiente de produção há um endereço para cada estado da federação. 
Neste caso, cada laboratório, conforme o CNES em questão, fará uso do estado correspondente. 
Por exemplo, o laboratório Rômulo Rocha, CNES 2337991, localizado no Estado de Goiás, fará uso do
endereço **https<span>:</span>//go-ehr-services.saude.gov.br/api**. 
