## Bibliotecas para acesso a RNDS

A construção de código que interage com a RNDS pode ser facilitada por meio de biblioteca que ofereça 
facilidades para realização das operações previstas, ocultando do desenvolver os detalhes da FHIR REST API. 

Adicionalmente, além de ocultar detalhes da API supracitada, serviços de maior nível de abstração devem ser fornecidos.
Por exemplo, a submissão de exame de COVID-19 pode receber os valores necessários para um resultado específico e
também montar o _payload_ que será enviado por meio da API. 

## Linguagens a serem contempladas

- [Java](rnds-java)
- C# (não iniciado)
- [node](rnds-js)
- Go (não iniciado)
- Python (não iniciado)

## Características das bibliotecas

- Bibliotecas deverão ser distribuídas pelos canais convencionais da plataforma em questão. Por exemplo, este já é o caso da versão já disponível em [node](https://www.npmjs.com/package/rnds).
- A expectativa é de que o mesmo conjunto funcional seja oferecido por todas as implementações, embora este alinhamento flutue com o acréscimo de novas funções.
- A funcionalidade básica pode ser ilustrada pela versão [node](https://github.com/kyriosdata/rnds/tree/master/libs/rnds-js). Outras funções de mais alto nível ainda não contam com _design_ correspondente. 
- A documentação deve incluir primeiros passos, identificação clara de pré-requisitos e conter exemplos. 
- As funções são definidas pelas operações oferecidas pela RNDS. Apenas o que for publicamente disponibilizado pela RNDS deverá ser contemplado.
- Deve considerar aspectos de segurança, como reutilização do token de acesso.
- Deve considerar a possibilidade do serviço da RNDS estar indisponível, por exemplo, por meio de [circuit breaker](https://www.baeldung.com/spring-boot-resilience4j). 

## Links
- Java https://hapifhir.io/hapi-fhir/apidocs/hapi-fhir-structures-r4/org/hl7/fhir/r4/utils/client/FHIRToolingClient.html
