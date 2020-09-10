---
id: postman
title: Primeiros contatos
sidebar_label: Primeiros contatos
---

A adoção do padrão FHIR significa "independência tecnológica" dos laboratórios em relação à RNDS. Ou seja, a RNDS não impõe, restringe ou orienta decisões
no escopo do ecossistema do laboratório. Ou seja, as tecnologias empregadas e como são utilizadas no âmbito do laboratório (nó Laboratório na figura abaixo), é feito com a total autonomia do laboratório.

![img](../static/img/rnds-deployment.png)

Em algum momento, contudo, requisições _https_ devem partir do laboratório e atingir o [ambiente](./ambientes) de produção da RNDS (neste guia é feito uso do ambiente de homologação). Ou seja, as portas Auth e EHR ilustradas acima.

O foco aqui são justamente tais requisições, conforme ressaltadas na figura abaixo. Observe que em vez de um [Software de Integração](./si), a ser desenvolvido pelo laboratório e ilustrado na figura acima, emprega-se o Postman como instrumento para experimentar as requisições, alterar valores de _headers_ e outros.

![img](../static/img/postman-desenvolvedor.png)

Ao final, espera-se que:

- Você saiba quais são as requisições disponíveis.
- Você saiba quais são os _headers_ necessários e como montar os valores correspondentes.
- Você saiba quais os dados a serem enviados por cada requisição, ou [payload](<https://en.wikipedia.org/wiki/Payload_(computing)>).
- Você saiba submeter as requisições por meio da ferramenta Postman.
- Você saiba experimentar valores diferentes para o _payload_, _headers_ e observar os resultados.
- Você saiba interagir com a RNDS usando HTTPS.
- Você saiba empregar o certificado digital do laboratório.
- Você saiba como realizar as atividades acima empregando a linguagem de programção Java.
- Você esteja apto para construir o [Software de Integração](./si).

### Pré-requisitos

- Informações:
  - Certificado digital. O arquivo correspondente deve estar disponível, é um arquivo com a extensão **.pfx**, aqui será referenciado por **certificado.pfx**.
  - Credenciamento homologado. O credenciamento é feito pelo [portal de serviços](https://servicos-datasus.saude.gov.br/). É preciso aguardar a homologação. Sem o credenciamento aprovado não é possível ter acesso ao [ambiente](./ambientes) de homologação (usado para os "primeiros contatos").
  - Identificador do laboratório fornecido pela RNDS. Este identificador é disponibilizado pela RNDS (veja [identificador do laboratório](./identificador)).
  - CNES. O CNES do laboratório.
  - O CNS de um profissional de saúde, lotado no laboratório cujo CNES é fornecido acima. O CNS indica em nome do quem as requisições ao ambiente de homologação serão feitas.
- Postman. A ferramenta Postman é empregada para interação com _web services_. O _Guia_ emprega ela para ilustrar os "primeiros contatos" com a RNDS. Detalhes `criar página com detalhes`.
- Baixe o arquivo JSON, empregado pelo Postman, contendo as [requisições](https://raw.githubusercontent.com/kyriosdata/rnds/3e92565e6e7fefd4020e89073166d9282510f2c2/tools/postman/rnds-postman-collection.json) a serem submetidas ao ambiente de homologação. Detalhes das requisições podem ser obtidos interagindo com o próprio postman, após a importação deste arquivo (seção seguinte) ou, se preferir, consulte a [documentação](https://documenter.getpostman.com/view/215332/TVCmT68w) correspondente na internet.

### Importar

De posse das informações necessárias (pré-requisitos), pertinentes a um dado laboratório, é necessário importar o arquivo contendo as [requisições](https://raw.githubusercontent.com/kyriosdata/rnds/3e92565e6e7fefd4020e89073166d9282510f2c2/tools/postman/rnds-postman-collection.json). Este arquivo contém, na terminologia empregada pelo Postman, uma _collection_ (as requisições).

> Veja o vídeo acerca de como importar [aqui](https://drive.google.com/file/d/13hbA4uZlX_90wFt0ktCvkX2jBbhFkoDC/view)

Ao abrir o Postman você verá uma tela similar àquela abaixo, exceto que não terá o destaque para o botão `Import`, empregado para "importar" o arquivo baixado anteriormente:

![postman-abertura](https://user-images.githubusercontent.com/1735792/92666083-a437c000-f2de-11ea-8ffc-9dd163784983.jpg)

Após importado, o resultado deve ser similar àquele abaixo. A versão pode ser diferente, por exemplo. Observe que estão disponíveis 11 requisições, agrupadas naquelas de "Segurança" e "Saúde".

![image](https://user-images.githubusercontent.com/1735792/92743020-8efe7800-f356-11ea-871e-3a4c6489ccfc.png)

Você pode experimentar a execução de qualquer uma delas, todas devem
falhar, ótimo (por enquanto, claro). É preciso, para funcionar, que as informações específicas
para o laboratório em questão sejam configuradas (próximo passo).

### Configurar e testar

- Configurar o certificado digital e executar obter token.

- Configurar variáveis empregadas pela collection
- Executar a primeira requisição satisfatória.

### E depois?

Parabéns, os "primeiros contatos" com a RNDS já foram feitos! Estão apresentados um ao outro. Agora é desenvolver o que aqui é chamado de [Software de Integração](./si). Embora este desenvolvimento seja atribuição de cada laboratório, e cada um possui suas especificidades, isto não inviabiliza mais um passo na direção de facilitar esta integração via. O [Software de Integração](./si), neste sentido, ilustra como a conexão com a RNDS pode ser feita via código.
