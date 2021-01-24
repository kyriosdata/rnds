## Objetivo

Ilustrar o acesso a um Servidor FHIR, por meio de pequenos
programas em Java, empregando a biblioteca HAPI FHIR.

> O uso em produção desta biblioteca provavelmente exige
> projeto (_design_) diferente daquele dos exemplos.

## Resultado esperado

O desenvolvedor (integrador) saberá como empregar a bibliteca
HAPI FHIR para acesso a um Servidor FHIR, por exemplo, aquele
disponibilizado pela RNDS.

## Experimente

Você pode clonar o repositório e fazer suas próprias mudanças
para experimentar a flexibilidade da biblioteca.

## Como executar cada exemplo?

Via linha de comandos, primeiro compile-os e, na sequência,
execute um deles. Abaixo é ilustrada a execução da
aplicação disponível na classe **com.github.kyriosdata.rnds.ForcaConexao**.
Em tempo, os exemplos fazem uso do servidor http://hapi.fhir.org/baseR4,
definido no próprio código-fonte.

- `mvn compile`
- `mvn exec:java -Dexec.mainClass="com.github.kyriosdata.rnds.ForcaConexao"`

## Exemplos

- `com.github.kyriosdata.rnds.ForcaConexao`  
  Força conexão com servidor definido no próprio código. Se
  não for possível conexão, uma exceção é gerada.

- `com.github.kyriosdata.rnds.BuscaPacientes`  
  Verifica se há pelo menos um paciente no servidor consultado.
