### FHIR (Fast Healthcare Interoperability Resources)

[<img src="https://www.hl7.org/fhir/assets/images/fhir-logo-www.png" width="100">](https://www.hl7.org/fhir/)

Conforme o portal https://hl7.org/fhir/, FHIR _é um padrão para a troca de dados em saúde_, o acrônimo vem de _Fast Healthcare Interoperability Resources_. Destacado no próprio nome, _resource_ ou recurso é o elemento básico empregado para a troca de dados usando FHIR.

Um recurso representa algum tipo de entidade do cuidado em saúde. Por exemplo, o recurso [_Patient_](https://www.hl7.org/fhir/patient.html) é empregado para dados demográficos ou outra informação administrativa acerca do indivíduo ou animal assistido. Por outro lado, se o que se deseja trocar são medidas como pressão ou temperatura, por exemplo, então fará uso do recurso [_Observation_](https://www.hl7.org/fhir/observation.html). No momento em que esta página é escrita estão definidos 145 tipos distintos de [recursos](https://www.hl7.org/fhir/resourcelist.html). Todos eles devidamente documentados.

## Forge (editor de perfis)

O FHIR visa contemplar um conjunto razoável de cenários, mas não é possível abarcar os usos específicos de todo o planeta. Felizmente, o FHIR permite "personalizações" por meio de perfis (_profiles_).

Embora a personalização seja possível usando um simples editor de texto,
é melhor realizada com apoio de um editor específico como o
[Forge](https://simplifier.net/forge), disponível para Windows.

A RNDS já realizou "adequações" em alguns dos recursos (_resources_) FHIR para atender o contexto nacional. As definições pertinentes estão
agrupadas e disponíveis neste arquivo [zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/9c3445f12823fd4c4f66e107617fc131_inp88qqqi.zip).

OBSERVAÇÃO: a edição de perfis não é uma tarefa típica de desenvolvedores de software, mas de quem é responsável pela modelagem de informação em saúde. 

## JSON (há ainda XML e RDF)

Quando se usa o FHIR para troca de dados, o que ocorre é a troca de recursos. Em particular, tais recursos trafegam representados em JSON, conforme explorado neste portal. Contudo, há ainda as opções XML e RDF.

O portal oficial define [JSON](https://www.json.org/json-en.html) (JavaScript Object Notation) como _um formato leve para troca de dados_. Ainda acrescenta que este formato é _fácil para seres humanos escreverem e lerem_.

[XML](https://en.wikipedia.org/wiki/XML), à semelhança de JSON, é comumente empregado em _web services_ (serviços oferecidos por meio da internet). Por outro lado, [RDF](https://www.hl7.org/fhir/rdf.html) está associado, em geral, a questões semânticas, por exemplo, quando se deseja realizar inferência sobre os dados.

## Hapi FHIR (implementação de referência)

[<img src="https://hapifhir.io/hapi-fhir/images/logos/raccoon-forwards.png" width="50">](https://hapifhir.io)

FHIR é uma especificação, um padrão. Computador, por outro lado, precisa de um software que implementa este padrão para ser executado. Há várias implementações de FHIR e algumas são [_open source_](https://wiki.hl7.org/Open_Source_FHIR_implementations).

A implementação de referência do FHIR, a [Hapi FHIR](https://hapifhir.io), é _open source_.<br>

A RNDS oferece uma implementação do FHIR por meio da qual laboratórios e outros estabelecimentos de súde consultam e enviam informações de saúde sobre usuários. Esta implementação é oferecida por meio dos [ambientes](./ambientes.md) de homologação e produção.

## Simplifier.NET

[<img src="https://simplifier.net/images/simplifier-logo.png" width="150">](https://simplifier.net)

Simplifier.NET é um serviço que permite
publicar perfis. Ou seja, você pode consultar as "personalizações" realizadas
pela RNDS no arquivo [zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/9c3445f12823fd4c4f66e107617fc131_inp88qqqi.zip),
também mencionado no item anterior. Contudo, o Simplifier.NET oferece uma alternativa de acesso via navegador com funções que facilitam a compreensão dos
perfis criados.

Os perfis definidos pela RNDS estão disponíveis para consulta, por meio deste serviço, [aqui](https://simplifier.net/RedeNacionaldeDadosemSade).

## Experimentação do FHIR

Há [vários](https://wiki.hl7.org/Publicly_Available_FHIR_Servers_for_testing) servidores disponíveis para experimentação com o FHIR. Ou seja, são computadores que estão executando alguma implementação do FHIR e não cobram nada por isso (lembre-se que são empregados apenas para testes, experimentações).

Cada um destes servidores funciona como um estabelecimento apto a interagir com outros por meio do FHIR. Desta forma, sem exigências que são necessárias em um cenário real, até porque os dados disponibilizados não são dados reais, você pode submeter requisições e observar os recursos retornados no formato da sua escolha, em geral XML ou JSON.

Em tempo, a implementação de referência do FHIR, Hapi FHIR, citada acima, também possui um servidor para testes disponível em http://hapi.fhir.org/.

## Mas como interagir com um destes servidores? (RESTful API?)

Embora existam outras formas de trafegar recursos (FHIR) entre dois computadores, o foco aqui está na RESTful API. O FHIR pode ser descrito como uma RESTful API. De forma simplificada, tal RESTful API descreve como interagir com uma implementação FHIR (por exemplo, qualquer uma daquelas em execução nos servidores citados na seção anterior).

A interação permite: ler o estado corrente de um recurso; ler o estado de um recurso em uma dada versão; atualizar um recurso existente por meio do seu identificador único; remover um recurso e até recuperar o histórico de mudanças de um recurso. Estas não são as únicas possibilidades. Também é possível procurar por um recurso utilizando algum critério de busca, dentre outras possibilidades.

### cURL

Entendi, mas como exatamente posso construir uma requisição em uma RESTful API? E mais específico ainda, exatamente a RESTful API definida pelo FHIR? Os detalhes estão amplamente disponíveis [aqui](http://hl7.org/fhir/http.html).

Você pode executar o comando abaixo em um _prompt_:

```
curl -H "Accept: application/json" http://test.fhir.org/r4/Patient/0c89be2f-121a-4b31-b9c8-d7528179fb
```

### Outras opções

Você também pode usar a interface gráfica oferecida pelo mesmo servidor empregado acima, ou seja, http://test.fhir.org/r4, ou ainda fazer uso de um software desenvolvido especificamente para a finalidade pretendida, seja ele para ser executado em um smartphone, computador usando um navegador ou um software que já é do conhecimento do usuário em questão e que agora está passando por uma manutenção na qual ele poderá recuperar dados em saúde além de um paciente, por exemplo, sem que o usuário sequer saiba que existe algo como FHIR.

- [.Net](http://ewoutkramer.github.io/fhir-net-api/client-setup.html)
- [JavaScript](https://github.com/smart-on-fhir/client-js)
- [Java](https://github.com/FirelyTeam/fhirstarters/tree/master/java/hapi-fhirstarters-client-skeleton)

## Requisições (como submeter)

A ferramenta [Postman](https://www.getpostman.com/downloads/) é amplamente empregada para submeter requisições HTTP.

Os primeiros passos em FHIR podem ser orientados por [aqui](https://blog.heliossoftware.com/fhir-training-the-early-steps-of-mastering-hl7-fhir-997d8dfa1320).

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
