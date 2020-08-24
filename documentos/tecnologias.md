### FHIR (Fast Healthcare Interoperability Resources)

[<img src="https://www.hl7.org/fhir/assets/images/fhir-logo-www.png" width="100">](https://www.hl7.org/fhir/)

Conforme o portal https://hl7.org/fhir/, FHIR _é um padrão para a troca de dados em saúde_, o acrônimo vem de _Fast Healthcare Interoperability Resources_. Destacado no próprio nome, _resource_ ou recurso é o elemento básico empregado para a troca de dados usando FHIR.

Um recurso representa algum tipo de entidade do cuidado em saúde. Por exemplo, o recurso [_Patient_](https://www.hl7.org/fhir/patient.html) é empregado para dados demográficos ou outra informação administrativa acerca do indivíduo ou animal assistido. Por outro lado, se o que se deseja trocar são medidas como pressão ou temperatura, por exemplo, então fará uso do recurso [_Observation_](https://www.hl7.org/fhir/observation.html). No momento em que esta página é escrita estão definidos 145 tipos distintos de [recursos](https://www.hl7.org/fhir/resourcelist.html). Todos eles devidamente documentados.

Está além do escopo apresentar em abrangência o FHIR. Os primeiros passos em FHIR podem ser orientados por [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320).

### Forge (editor de perfis)

O FHIR visa contemplar um conjunto razoável de cenários, mas não é possível abarcar os usos específicos de todo o planeta.
Felizmente, o FHIR permite "personalizações" por meio de perfis (_profiles_).
A definição de perfis é um esforço de modelagem de informação em saúde, e não é uma tarefa típica de desenvolvedores de software. Contudo,
desenvolvedores terão que ter acesso aos perfis estabelecidos para os recursos a serem trocados. Afinal, toda a troca de
dados deverá estar em conformidade com os perfis definidos.

Um perfil pode ser criado com um simples editor de texto, contudo,
a criação é melhor realizada com apoio de um editor específico como o
[Forge](https://simplifier.net/forge), disponível para Windows.

### Perfis RNDS (definições)

A RNDS já realizou "adequações" em alguns dos recursos (_resources_) FHIR para atender o contexto nacional. As definições pertinentes estão
agrupadas e disponíveis no arquivo [zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/9c3445f12823fd4c4f66e107617fc131_inp88qqqi.zip).

Além deste arquivo, as definições
podem ser publicadas e consultadas por meio do portal Simplifier.NET (comentado abaixo), cujas funções incluem recursos de
navegação e visualização das definições. Os perfis definidos pela RNDS estão disponíveis para consulta, por meio deste serviço, [aqui](https://simplifier.net/RedeNacionaldeDadosemSade).

### Simplifier.NET

[<img src="https://simplifier.net/images/simplifier-logo.png" width="150">](https://simplifier.net)

Simplifier.NET é um serviço que permite
publicar e consultar perfis. Convém ressaltar que perfis precisam ser conhecidos entre as partes que irão interagir.

As facilidades oferecidas pelo Simplifier.NET incluem a navegação entre recursos e definições pertinentes, o que é desejável para
documentação.

## JSON (há ainda XML e RDF)

Quando se usa o FHIR para troca de dados, o que ocorre é a troca de recursos. Em particular, tais recursos trafegam representados em JSON, conforme explorado neste portal. Contudo, há ainda as opções XML e RDF.

O portal oficial define [JSON](https://www.json.org/json-en.html) (JavaScript Object Notation) como _um formato leve para troca de dados_. Ainda acrescenta que este formato é _fácil para seres humanos escreverem e lerem_.

[XML](https://en.wikipedia.org/wiki/XML), à semelhança de JSON, é comumente empregado em _web services_ (serviços oferecidos por meio da internet). Por outro lado, [RDF](https://www.hl7.org/fhir/rdf.html) está associado, em geral, a questões semânticas, por exemplo, quando se deseja realizar inferência sobre os dados.

## Hapi FHIR (implementação de referência)

[<img src="https://hapifhir.io/hapi-fhir/images/logos/raccoon-forwards.png" width="50">](https://hapifhir.io)

FHIR é uma especificação, um padrão. Computador, por outro lado, precisa de um software que implementa este padrão para ser executado. Há várias implementações de FHIR e algumas são [_open source_](https://wiki.hl7.org/Open_Source_FHIR_implementations).

A implementação de referência do FHIR, a [Hapi FHIR](https://hapifhir.io), é _open source_.<br>

A RNDS oferece uma implementação do FHIR por meio da qual laboratórios e outros estabelecimentos de súde consultam e enviam informações de saúde sobre usuários. Esta implementação é oferecida por meio dos [ambientes](./ambientes.md) de homologação e produção.

## Experimentar acesso ao FHIR (sem ser via RNDS)

Há [vários](https://wiki.hl7.org/Publicly_Available_FHIR_Servers_for_testing) servidores disponíveis para experimentação com o FHIR. Ou seja, são computadores que estão executando alguma implementação do FHIR e não cobram nada por isso (lembre-se que são empregados apenas para testes, experimentações).

Cada um destes servidores funciona como um estabelecimento apto a interagir com outros por meio do FHIR. Desta forma, sem exigências que são necessárias em um cenário real, até porque os dados disponibilizados não são dados reais, você pode submeter requisições e observar os recursos retornados no formato da sua escolha, em geral XML ou JSON.
Desenvolvedores podem empregar estes servidores para se ambientar com a RESTful API padronizada pelo FHIR. De fato, o acesso ao [ambiente](./ambientes.md) de homologação pode ser precedido pelo contato
com um destes servidores.

Em tempo, a implementação de referência do FHIR, Hapi FHIR, citada acima, também possui um servidor para testes disponível em http://hapi.fhir.org/.

## Como interagir com um servidor FHIR?

Há uma API (_Application Programming Interface_) bem-definida para o acesso
a um servidor FHIR, uma RESTful API, e há várias formas de usar esta API.

Consulte [What is REST](https://restfulapi.net/) para saber o que é uma RESTful API, e portais [pertinentes](https://github.com/Kikobeats/awesome-api). Este estilo de integração é amplamente empregado e você encontrará com facilidade os mais variados serviços acessíveis
por meio de uma RESTful API (veja uma lista deles [aqui](https://medium.com/better-programming/a-curated-list-of-100-cool-and-fun-public-apis-to-inspire-your-next-project-7600ce3e9b3)).

### RESTful API FHIR

Um dos aspectos que distingue o FHIR de outras estratégias de interoperabilidade é o foco na implementação. Em particular, inclui detalhes da interface de acesso,
ou seja, da [RESTful API FHIR](http://hl7.org/fhir/http.html). Para definição de
RESTful API consulte .

### Interface gráfica

Alguns deles oferecem uma interface gráfica onde é possível "montar" uma
requisição e submetê-la. O servidor de testes [Hapi FHIR](http://hapi.fhir.org/) é um exemplo. É útil para experimentação e para obter detalhes de como criar corretamente uma requisição.

### cURL

Via linha de comandos (_shell_) você pode disparar requisições compatíveis com a RESTful API FHIR. Naturalmente, este é um recurso empregado mais por desenvolvedores. Afinal, é necessário indicar explicitamente todos os _headers_ necessários, assim como o _payload_ esperado, para montar corretamente uma requisição. Você pode executar o comando abaixo

```
curl -H "Accept: application/json" http://test.fhir.org/r4/Patient/id-invalido
```

e obter como resposta o código 404 (não encontrado), para indicar que o paciente
cujo identificador é "id-invalido" não foi encontrado. Observe que esta requisição
faz uso do servidor FHIR **http<span>:</span>//test.fhir.org**.

### Postman

<img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIzLjAuMywgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkxheWVyXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IgoJIHdpZHRoPSIxMDk3LjdweCIgaGVpZ2h0PSIzMzMuMXB4IiB2aWV3Qm94PSIwIDAgMTA5Ny43IDMzMy4xIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCAxMDk3LjcgMzMzLjE7IgoJIHhtbDpzcGFjZT0icHJlc2VydmUiPgo8c3R5bGUgdHlwZT0idGV4dC9jc3MiPgoJLnN0MHtmaWxsOiNGRkZGRkY7fQoJLnN0MXtmaWxsOiNGRjZDMzc7fQo8L3N0eWxlPgo8dGl0bGU+aG9yLXdoaXRlLWxvZ288L3RpdGxlPgo8Zz4KCTxnIGlkPSJMYXllcl8yLTIiPgoJCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik0xODYuNyw5LjNDOTkuNy0xLjksMjAuMSw1OS42LDksMTQ2LjZzNTAuMywxNjYuNiwxMzcuMywxNzcuN0MyMzMuMywzMzUuNSwzMTIuOSwyNzQsMzI0LDE4NwoJCQljMC0wLjEsMC0wLjIsMC0wLjJDMzM1LjEsOTkuOCwyNzMuNiwyMC40LDE4Ni43LDkuM3oiLz4KCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE5LjIsMTEzLjdoLTI3LjFjLTEuMy0wLjEtMi42LDAuNC0zLjUsMS4yYy0wLjksMC43LTEuNCwxLjgtMS40LDN2OTZjMCwxLjMsMC44LDIuNiwyLDMuMgoJCQljMi42LDEuNiw1LjksMS42LDguNSwwYzEuMy0wLjYsMi4xLTEuOCwyLjEtMy4ydi0zNy4yaDE4LjZjOC4xLDAuNCwxNi4xLTIuMywyMi40LTcuNWM2LjEtNi4zLDkuMi0xNC45LDguNS0yMy42di0xLjMKCQkJYzAuOC04LjUtMi4xLTE3LTcuOS0yMy4zQzQzNS4xLDExNS44LDQyNy4yLDExMy4yLDQxOS4yLDExMy43eiBNNDM3LjgsMTQ2LjdjMC40LDUuNS0xLjQsMTEtNC45LDE1LjJjLTMuOSwzLjgtOS4xLDUuNy0xNC41LDUuNAoJCQloLTE4LjZ2LTQyLjhoMTguNmM1LjItMC4zLDEwLjMsMS42LDE0LDUuMWMzLjYsNC4yLDUuNCw5LjcsNC45LDE1LjJMNDM3LjgsMTQ2Ljd6Ii8+CgkJPHBhdGggY2xhc3M9InN0MCIgZD0iTTUwMCwxMjAuOGMtNi4xLDYuMy05LjEsMTUtOC40LDIzLjd2NDMuMWMtMC44LDguOCwyLjMsMTcuNCw4LjQsMjMuOGMxMy4zLDEwLjEsMzEuNywxMC4xLDQ1LDAKCQkJYzYuMi02LjMsOS4zLTE1LDguNS0yMy44di00Mi44YzAuOS04LjktMi4yLTE3LjctOC41LTI0LjFDNTMxLjcsMTEwLjcsNTEzLjMsMTEwLjcsNTAwLDEyMC44eiBNNTQxLjQsMTg3LjcKCQkJYzAuNSw1LjYtMS4zLDExLjEtNC45LDE1LjNjLTMuNywzLjYtOC44LDUuNC0xMy45LDUuMWMtMTIuNiwwLTE4LjYtNi45LTE4LjYtMjAuNXYtNDIuOGMwLTEzLjYsNi4yLTIwLjQsMTguNi0yMC40CgkJCWM1LjItMC4zLDEwLjIsMS41LDEzLjksNS4xYzMuNiw0LjIsNS40LDkuNyw0LjksMTUuMlYxODcuN3oiLz4KCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNjQxLjUsMTYyYy00LjctMi4xLTkuMy00LTEzLjktNS43Yy00LjMtMS40LTguNC0zLjYtMTEuOS02LjVjLTMuMi0yLjYtNC45LTYuNy00LjYtMTAuOAoJCQljLTAuNC00LjUsMS43LTguOCw1LjQtMTEuM2M0LjItMi41LDkuMS0zLjcsMTMuOS0zLjRjNC42LTAuMSw5LjIsMC44LDEzLjQsMi43YzIuMSwxLDQuMiwxLjksNi40LDIuNmMxLjYsMCwzLTAuOSwzLjctMi4zCgkJCWMwLjgtMS4zLDEuNC0yLjgsMS41LTQuM2MwLTIuOS0yLjgtNS4zLTguMi03LjFjLTUuNS0xLjctMTEuMi0yLjYtMTctMi41Yy0zLjcsMC03LjQsMC40LTExLDEuM2MtMy41LDAuOS02LjksMi4yLTEwLDQuMQoJCQljLTMuMywyLTUuOSw0LjktNy42LDguNGMtMiw0LjEtMyw4LjYtMi45LDEzLjJjLTAuMiw1LjYsMS41LDExLjIsNC44LDE1LjdjMy4xLDQsNy4yLDcuMiwxMS45LDkuM2M0LjcsMiw5LjMsMy44LDEzLjksNS41CgkJCWM0LjQsMS42LDguNSw0LjIsMTEuOCw3LjVjMy4zLDMuNSw1LDguMSw0LjgsMTIuOGMwLjQsNS0xLjYsOS45LTUuMywxMy4zYy00LjIsMy4zLTkuNCw0LjgtMTQuNyw0LjVjLTMuNiwwLjEtNy4xLTAuNS0xMC40LTEuOAoJCQljLTIuNS0wLjgtNC44LTIuMi02LjgtNGwtNC41LTQuM2MtMC45LTAuOS0yLjEtMS42LTMuMy0xLjhjLTEuNCwwLjEtMi43LDAuOS0zLjQsMi4xYy0xLDEuMy0xLjYsMi44LTEuNyw0LjVjMCwzLjQsMyw3LDkuMywxMC41CgkJCWM2LjUsMy44LDE0LDUuNywyMS42LDUuNGM4LjMsMC40LDE2LjQtMi4zLDIyLjgtNy41YzYuMS01LjgsOS4yLTE0LDguNi0yMi4zQzY1OC43LDE3OCw2NTIuMiwxNjcsNjQxLjUsMTYyTDY0MS41LDE2MnoiLz4KCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNzU2LjYsMTEzLjdoLTU4LjRjLTEuMywwLTIuNCwwLjYtMy4yLDEuNmMtMS42LDIuMy0xLjYsNS40LDAsNy43YzAuNywxLDEuOSwxLjYsMy4yLDEuNmgyMy4ydjg5LjMKCQkJYzAsMS4zLDAuNywyLjUsMS44LDMuMmMyLjYsMS42LDUuOSwxLjYsOC41LDBjMS4yLTAuNiwxLjktMS44LDEuOS0zLjJ2LTg5LjNoMjIuOWMxLjMsMC4xLDIuNS0wLjYsMy4yLTEuNwoJCQljMC44LTEuMSwxLjItMi41LDEuMS0zLjljMC0xLjMtMC4zLTIuNi0xLjEtMy43Qzc1OC45LDExNC4zLDc1Ny44LDExMy43LDc1Ni42LDExMy43eiIvPgoJCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik04NzIuMywxMTMuN2MtMy42LDAtNi44LDIuNS05LjMsNy42bC0yMi43LDQzLjFsLTIyLjUtNDMuMWMtMi42LTUuMS01LjgtNy42LTkuMy03LjZIODA2CgkJCWMtMi44LDAuNS00LjcsMy4yLTQuMyw2djk0LjFjMCwxLjQsMC43LDIuNywyLDMuM2MxLjMsMC44LDIuOCwxLjIsNC4zLDEuMWMxLjQsMCwyLjgtMC41LDQtMS4zYzEuMi0wLjYsMS45LTEuOCwxLjktMy4ydi03NS42CgkJCWwyMS43LDQxLjRjMC42LDIsMi4zLDMuNCw0LjQsMy41YzIuMi0wLjEsNC0xLjUsNC43LTMuNWwyMS43LTQwLjd2NzQuOWMwLDEuMywwLjcsMi41LDEuOCwzLjJjMi42LDEuNSw1LjgsMS41LDguNSwwCgkJCWMxLjItMC42LDEuOS0xLjgsMS45LTMuMnYtOTQuM2MwLjEtMC41LDAuMS0xLDAtMS41bDAsMEM4NzcuOSwxMTUuMiw4NzUuMiwxMTMuMiw4NzIuMywxMTMuN3oiLz4KCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNOTU2LjUsMTEzLjhjLTQuMSwwLTYuNSwxLjQtNy4zLDQuMWwtMjkuNCw5NC41Yy0wLjEsMC40LTAuMSwwLjcsMCwxLjFjMCwxLjYsMC45LDMuMSwyLjMsMy44CgkJCWMxLjUsMC45LDMuMiwxLjQsNC45LDEuNGMxLjksMC4zLDMuOC0wLjksNC40LTIuOGw2LjItMjAuOWgzNy4ybDYuMiwyMC45YzAuNiwxLjgsMi41LDMsNC40LDIuN2MxLjgsMC4xLDMuNy0wLjQsNS4yLTEuNAoJCQljMS40LTAuNiwyLjMtMS45LDIuMy0zLjQgTTk5MywyMTMuNHYtMC41bC0yOS40LTk1Yy0wLjctMi43LTMuMS00LjEtNy4yLTQuMUw5OTMsMjEzLjR6IE05NDAuNCwxODQuOGwxNi01My42bDE2LjEsNTMuNgoJCQlMOTQwLjQsMTg0LjhMOTQwLjQsMTg0Ljh6Ii8+CgkJPHBhdGggY2xhc3M9InN0MCIgZD0iTTEwOTYsMTE0LjdjLTIuNi0xLjUtNS44LTEuNS04LjUsMGMtMS4xLDAuNi0xLjgsMS44LTEuOSwzLjF2NzMuNWwtMzUuNy02OS45Yy0wLjktMi4yLTIuMy00LjItNC01LjkKCQkJYy0xLjQtMS4yLTMuMi0xLjgtNS4xLTEuOGMtNC4xLDAtNiwxLjQtNiw0LjJ2OTZjLTAuMSwxLjMsMC42LDIuNSwxLjgsMy4yYzIuNiwxLjYsNS45LDEuNiw4LjUsMGMxLjEtMC42LDEuOC0xLjksMS44LTMuMlYxNDEKCQkJbDM3LjIsNzIuMWMxLjIsMy4yLDQuMiw1LjMsNy41LDUuNWMxLjUsMCwzLTAuNCw0LjMtMS4yYzEuMS0wLjcsMS44LTEuOSwxLjgtMy4ydi05Ni40QzEwOTcuNywxMTYuNSwxMDk3LjEsMTE1LjQsMTA5NiwxMTQuN3oiLz4KCQk8cGF0aCBjbGFzcz0ic3QxIiBkPSJNMjYxLjgsODMuN2MtMC45LDAuNC0xLjQsMS41LTEsMi40YzAsMC4xLDAuMSwwLjEsMC4xLDAuMmMxLDIsMC44LDQuNS0wLjcsNi4yYy0wLjcsMC43LTAuOCwxLjktMC4xLDIuNgoJCQljMCwwLDAsMCwwLjEsMC4xYzAuNCwwLjEsMC45LDAuMSwxLjMsMGMwLjUsMCwxLjEtMC4zLDEuNC0wLjdjMi41LTIuOSwzLTcsMS4yLTEwLjRDMjYzLjYsODMuNCwyNjIuNiw4My4yLDI2MS44LDgzLjd6Ii8+CgkJPHBhdGggY2xhc3M9InN0MSIgZD0iTTE4Ny43LDEuNEM5Ni41LTEwLjQsMTMuMSw1NC4xLDEuNCwxNDUuM1M1NC4xLDMyMCwxNDUuMywzMzEuN1MzMTkuOSwyNzksMzMxLjcsMTg3LjgKCQkJQzM0My40LDk2LjYsMjc5LDEzLjEsMTg3LjcsMS40QzE4Ny44LDEuNCwxODcuOCwxLjQsMTg3LjcsMS40eiBNMjIyLDEwNS4yYy0zLjEsMC4xLTYsMS4zLTguMiwzLjVMMTUyLDE3MC41bC0xMy4yLTEzLjIKCQkJQzE5OS42LDk2LjYsMjEwLjcsOTYsMjIyLDEwNS4yeiBNMTU0LjUsMTcyLjdsNjEuNi02MS4yYzMuNC0zLjMsOC44LTMuMywxMi4yLDBjMS43LDEuNywyLjYsNCwyLjUsNi4zYy0wLjEsMi40LTEuMSw0LjYtMi45LDYuMgoJCQlsLTY1LjEsNTcuNEwxNTQuNSwxNzIuN3ogTTE1OS4xLDE4MmwtMTUuMywzLjljLTAuMywwLjItMC42LDAuMi0wLjksMGMtMC4yLTAuMy0wLjItMC43LDAtMWw5LjMtOS4zTDE1OS4xLDE4MnogTTEyMC4xLDE3NS42CgkJCWwxNi4zLTE2LjNsMTIuMywxMi42bC0yNy45LDZjLTAuNiwwLjEtMS4yLTAuMy0xLjMtMC45Yy0wLjEtMC40LDAtMC43LDAuMy0xTDEyMC4xLDE3NS42eiBNNjkuNiwyNjAuM2MtMC40LTAuMS0wLjgtMC40LTAuOS0wLjcKCQkJYy0wLjEtMC40LTAuMS0wLjgsMC0xLjFsMTMuMS0xMy4ybDE3LDE2LjlMNjkuNiwyNjAuM3ogTTEwMy4yLDI0Mi44TDEwMy4yLDI0Mi44Yy0xLjMsMC43LTIsMi4xLTEuNywzLjVsMi44LDEyCgkJCWMwLjIsMC44LTAuMiwxLjYtMC44LDJjLTAuNiwwLjUtMS41LDAuNS0yLDBsLTE3LTE3LjVsNTIuMi01Mi4zbDI1LjMtNS40bDEyLjEsMTIuMWMtMjEuNiwxOC00NS40LDMzLjMtNzAuOCw0NS40djAuMkgxMDMuMnoKCQkJIE0xNzYuNiwxOTUuMkwxNjUsMTgzLjZsNjUuMS01Ny4zYzAuNi0wLjUsMS4yLTEuMSwxLjctMS44QzIyOS43LDE0My4xLDIwMy41LDE2OS41LDE3Ni42LDE5NS4yeiBNMjI3LjQsMTA1LjUKCQkJYy05LjctMTAuMS05LjMtMjYuMSwwLjgtMzUuOGM5LjItOC44LDIzLjQtOS40LDMzLjMtMS40bC0yMi40LDIyLjNjLTAuNiwwLjctMC42LDEuNywwLDIuNGwxNy40LDE3LjNjLTkuOCw0LjgtMjEuNiwyLjgtMjkuMi01CgkJCUwyMjcuNCwxMDUuNXogTTI2My4xLDEwNS41Yy0xLjIsMS4xLTIuNCwyLjItMy44LDMuMWwtMTYuNy0xNi43bDIxLjMtMjEuM0MyNzMuMSw4MC41LDI3Mi44LDk1LjksMjYzLjEsMTA1LjV6Ii8+Cgk8L2c+CjwvZz4KPC9zdmc+Cg==" width="100px">

A ferramenta [Postman](https://www.getpostman.com/downloads/) é uma alternativa
para as requisições via linha de comandos usando cURL. É possível,
inclusive, configurar testes, dentre outras funções oferecidas por meio desta ferramenta.

### Outras opções

Você também pode usar a interface gráfica oferecida pelo mesmo servidor empregado acima, ou seja, http://test.fhir.org/r4, ou ainda fazer uso de um software desenvolvido especificamente para a finalidade pretendida, seja ele para ser executado em um smartphone, computador usando um navegador ou um software que já é do conhecimento do usuário em questão e que agora está passando por uma manutenção na qual ele poderá recuperar dados em saúde além de um paciente, por exemplo, sem que o usuário sequer saiba que existe algo como FHIR.

- [.Net](http://ewoutkramer.github.io/fhir-net-api/client-setup.html)
- [JavaScript](https://github.com/smart-on-fhir/client-js)
- [Java](https://github.com/FirelyTeam/fhirstarters/tree/master/java/hapi-fhirstarters-client-skeleton)

## FHIRPath

Respostas para requisições via FHIR são formatadas usando JSON. Para consultar tais documentos, por exemplo, extrair uma informação dentre os dados retornados, pode-se usar FHIRPath ([github](https://github.com/HL7/fhirpath)). A [especificação](http://hl7.org/fhirpath/) encontra-se amplamente disponível, assim como a versão [detalhada](https://github.com/HL7/FHIRPath/blob/master/spec/index.adoc)). FHIRPath é usado pela _Clinical Quality Language_ ([CQL](https://cql.hl7.org/index.html)).

Consultas baseadas em FHIRPath podem ser executadas por meio do portal [clinfhir](http://clinfhir.com). Adicionalmente, pode-se usar a implementação em [javascript](https://github.com/HL7/fhirpath.js), dentre outras opções.

```shell
npm install --global fhirpath
fhirpath -f resposta.json -e 'FHIRPath expression'
```

Convém destacar que documentos JSON em geral podem ser consultados por meio de JsonPath ([online](https://jsonpath.com/), [specification](https://goessner.net/articles/JsonPath/), [tutorial](https://www.baeldung.com/guide-to-jayway-jsonpath), [java](https://github.com/json-path/JsonPath)). Observe que JsonPath também pode ser empregado para consultar documentos JSON retornados via FHIR, contudo, FHIRPath contém recursos específicos.

# Siglas

- STU é a abreviação para _Standard for Trial Use_ no contexto FHIR.
