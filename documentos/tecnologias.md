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

[<img src="../media/postman.png" width="50px">](https://www.getpostman.com/downloads/)

A ferramenta [Postman](https://www.getpostman.com/downloads/) é uma alternativa
gráfica para montar requisições, executá-las, e configurar testes, dentre outras funções, o que a torna uma espécie de referência no desenvolvimento de APIs.

### Desenvolvedores via código

Desenvolvedores escrevem código. O acesso à RESTful API FHIR via código
é apoiada por várias bibliotecas. Abaixo seguem algumas delas:

- [.Net](http://ewoutkramer.github.io/fhir-net-api/client-setup.html)
- [JavaScript](https://github.com/smart-on-fhir/client-js)
- [Java](https://github.com/FirelyTeam/fhirstarters/tree/master/java/hapi-fhirstarters-client-skeleton)

## FHIRPath

FHIRPath é um mecanismo para manusear documentos JSON contendo recursos FHIR.
Trata-se de proposta similar à JsonPath e XML Path, por exemplo. Contudo,
possui funções específicas para documentos contendo recursos FHIR.
Consulte [FHIRPath](../tools/fhirpath) para detalhes, inclusive exemplos de uso.

# Siglas

- STU é a abreviação para _Standard for Trial Use_ no contexto FHIR.
