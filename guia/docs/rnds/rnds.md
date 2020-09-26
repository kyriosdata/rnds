---
id: rnds
title: RNDS
sidebar_label: Apresentação
---

Na perspectiva de um estabelecimento de saúde, a Rede Nacional de Dados em Saúde (RNDS) oferece serviços
para a interoperabilidade em saúde no território nacional. No Brasil, é por meio da RNDS que a "informação em saúde estará disponível
onde é necessária". 

Quando um estabelecimento de saúde se integra à RNDS, cria-se a possibilidade dele contribuir com informações em saúde pertinentes aos usuários que assiste e, ao mesmo tempo, consumir informações geradas por outros estabelecimentos. 

Conforme ilustrado abaixo, a integração com a RNDS segue o padrão [FHIR](../intro/glossario#fhir). Isso significa "independência tecnológica" dos estabelecimentos de saúde em relação à RNDS, que não impõe, não restringe ou nem tampouco orienta decisões no escopo do ecossistema do estabelecimento de saúde. De fato, a RNDS sequer sabe
qual o ecossistema empregado pelo estabelecimento de saúde. Em algum momento, contudo, requisições _https_, obdecendo o padrão FHIR e [perfis](../rnds/perfis) definidos pela RNDS, devem partir do estabelecimento de saúde e atingir o [ambiente](./ambientes) de produção da RNDS (neste guia é feito uso do ambiente de homologação). 

![interoperabilidade](../../static/img/rnds-brasil.png)

Os [serviços](./servicos) oferecidos, no momento, estão voltados para 
a notificação de resultados de exames de SARS-CoV-2. 

Tais serviços estão em conformidade com o padrão [FHIR](http://hl7.org/fhir/), adotado pelo Brasil, e com as [definições](./perfis) necessárias para acomodar as especificidades nacionais. 

O resultado de exame laboratorial, escopo do primeiro conjunto de serviços, encontra-se devidamente [detalhado](./resultado). Desta forma, exemplifica como o 
FHIR pode ser empregado em conformidade com os perfis estabelecidos
pelo Brasil.  

Por fim, tais serviços estão disponíveis por meio de dois [ambientes](./ambientes). São eles que viabilizam uma rede nacional de dados em saúde.  
