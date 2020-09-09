---
id: postman
title: Primeiros contatos
sidebar_label: Primeiros contatos
---

A adoção do padrão FHIR significa "independência tecnológica" dos laboratórios em relação à RNDS. Ou seja, a RNDS não impõe, restringe ou orienta decisões
no escopo do ecossistema do laboratório.

Em algum momento, contudo, requisições _https_ devem partir do laboratório e atingir o [ambiente](./ambientes) de produção da RNDS (neste guia é feito uso do ambiente de homologação).

> O foco aqui é o envio de requisições para o ambiente de homologação da RNDS.

Ao final, espera-se que:

- Você saiba quais são as requisições disponíveis.
- Você saiba quais são os _headers_ necessários e como montar os valores correspondentes.
- Você saiba quais os dados de fato a serem enviados por cada requisição, ou [payload](<https://en.wikipedia.org/wiki/Payload_(computing)>).l o _payload_ (dados enviados) exigido em cada requisição.

### Pré-requisitos

- Informações:
  - Certificado digital. O arquivo correspondente deve estar disponível, é um arquivo com a extensão **.pfx**, aqui será referenciado por **certificado.pfx**.
  - Credenciamento homologado. O credenciamento é feito pelo [portal de serviços](https://servicos-datasus.saude.gov.br/). É preciso aguardar a homologação. Sem o credenciamento aprovado não é possível ter acesso ao [ambiente](./ambientes) de homologação.
  - Identificador do laboratório fornecido pela RNDS. Este identificador é disponibilizado pela RNDS (veja [identificador do laboratório](./identificador)).
  - CNES. O CNES do laboratório.
  - O CNS de um profissional de saúde, lotado no laboratório cujo CNES é fornecido acima. O CNS indica em nome do quem as requisições ao ambiente de homologação serão feitas.
- Postman. A ferramenta Postman é empregada para interação com _web services_. O _Guia_ emprega ela para ilustrar os "primeiros contatos" com a RNDS. Detalhes `criar página com detalhes`.
- Baixe o arquivo contendo as [requisições](https://github.com/kyriosdata/rnds/blob/3e92565e6e7fefd4020e89073166d9282510f2c2/tools/postman/rnds-postman-collection.json). Trata-se de um arquivo JSON.

### E agora?

De posse das informações necessárias para a experimentação, siga os passos abaixo ou, se preferir, o vídeo explicativo.

### E depois?

Parabéns, os "primeiros contatos" com a RNDS já foram feitos! Estão apresentados um ao outro.

Detalhes das requisições podem ser obtidos interagindo com o próprio postman, ou se preferir, a [documentação](https://documenter.getpostman.com/view/215332/TVCiUn6P) correspondente.

As informações incluem a finalidade de cada requisição, todos os _headers_, o _payload_ e as respostas fornecidas para as requisições.
