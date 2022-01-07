# Design (fhirpathcli)

A figura abaixo ilustra dois cenários, aquele que atende os requisitos 
estabelecidos (Aplicação CLI) e um segundo que atende eventual 
necessidade futura (Aplicação Gráfica). Este último cenário é uma especulação.

![design](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/kyriosdata/rnds/master/tools/fhirpathcli/UML/design.puml)

Nesta figura há uma proposta de organização do código da Aplicação CLI com o 
propósito de destacar a biblioteca FHIRPath. Esta organização promove a 
eventual reutilização de código numa aplicação gráfica (especulação). 

Observe que dentre as opções está uma alternativa para a execução via
linha de comandos (console), o que permite isolar a tecnologia empregada
na linha de comandos de Java, por exemplo, uma aplicação em NodeJS que
obtém ou atualiza o serviço (FhirPath Service), assim como a si próprio,
inicia o serviço, interage com o usuário e repassa comandos e recebe 
as respostas do serviço. 

O detalhamento desta proposta segue abaixo em tópicos assim como outros
itens pertinentes ao _design_ de artefatos necessários para a aplicação CLI.

## Landing page
Página que disponibiliza o aplicativo para _download_ bem como orientações de uso. 
O _design_ desta página pode ser melhor conduzido com a definição de detalhes 
da aplicação. 

## Estratégia de implantação e execução
Executável único para as principais plataformas em formato nativo (MacOS, Linux e Windows). 
Simplifica a instalação e a atualização. A aplicação usa um único processo.
Observe que a "versão gráfica" (especulada), usa dois processos. 

Executável único não é um arquivo **jar** executável, mas um arquivo executável em cada uma das
plataformas citadas, e que não depende da disponibilizada da JVM. Também não é um instalador, mas o
aplicativo propriamente dito.

A GraalVM (https://www.graalvm.org/), ou similar, pode ser utilizada para esta finalidade.

## CLI (camadas)

O módulo CLI está organizado em camadas: (a) apresentação; (b) negócio e (c) dados. 

### Apresentação (camada)

A interface por meio da qual o usuário requisita a execução de sentenças em FHIRPath 
e consome os resultados. Inclui definição de teclas, leiaute, cores e outros. 
Depende, naturalmente, da linguagem de comandos (camada de negócio).

A interação com o usuário pode se beneficiar de ferramenta como 
[jline3](https://github.com/jline/jline3) ou similar. 
A visualização exige duas estratégias: uma para console (texto) e outra gráfica (html).

A versão gráfica (html) pode se inspirar em propostas como o
[artigo](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC6784845/) para visualização 
de instâncias de objetos FHIR e a 
[ferramenta](http://gotdan.github.io/fhirbrowser/index.html) pertinente.
Em tempo, a biblioteca [D3.js](https://d3js.org/) oferece recursos para 
visualização e interação com árvores. 

Uma outra opção para inspiração é o 
[cliente gráfico]((https://docs.microstream.one/manual/storage/rest-interface/client-gui.html)) 
para MicroStream. É empregado para objetos em Java, ou seja, “exatamente” o cenário em questão.

### Negócio (camada)

A principal função oferecida pela aplicação é a execução de sentenças em
FHIRPath, contudo, algumas operações de suporte fazem-se necessárias. 
Todas as funções são definidas pela linguagem de comandos abaixo.

### Dados (camada)
Inclui dados e gestão de configuração, os recursos utilizados, _cache_ e o 
histórico de sentenças e comandos. 

A persistência pode ser realizada via [MicroStream](https://microstream.one/). 
Convém observar que o aplicativo será fornecido via um único aplicativo executável.
Os dados devem ser armazenados nos seguintes diretórios, e a base deles via
`System.getProperty("user.home")`:
- Windows: `%USERPROFILE%\AppData\Local\fhirpathcli\` 
- Linux: `~/.config/fhirpathcli`
- MacOS: `/Users/username/Library/Preferences/fhirpathci` (precisa confirmar)

Esta camada também é responsável pela conversão entre JSON e XML, em 
ambos os sentidos. De fato, as conversões também incluem o formato Yaml. 
Conversões podem ser realizadas com o apoio de ferramentas como 
[Jackson](https://github.com/FasterXML/jackson). A conversão de recursos 
(objetos Java) de/para XML/JSON pode ser feita com o emprego da 
[HAPIFHIR](https://hapifhir.io/). A conversão de/para Yaml pode se 
beneficiar da ferramenta Jackson, citada anteriormente, 
conforme artigos JSON para Yaml 
[aqui](https://stackoverflow.com/questions/26235350/convert-json-to-yaml-parsing-json-to-yaml) 
e Yaml para JSON 
[aqui](https://stackoverflow.com/questions/23744216/how-do-i-convert-from-yaml-to-json-in-java).
 
