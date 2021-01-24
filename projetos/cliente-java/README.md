## Objetivo

Ilustrar o acesso a um Servidor FHIR, via vários pequenos
programas em Java, empregando a biblioteca HAPI FHIR.

## Resultado esperado

O desenvolvedor (integrador) saberá como empregar a bibliteca
HAPI FHIR para acesso a um Servidor FHIR, por exemplo, aquele
disponibilizado pela RNDS.

### Execução

- `mvn compile`
- `mvn exec:java -Dexec.mainClass="com.github.kyriosdata.rnds.ForcaConexao"`  
  Para executar a classe indicada.

### Classes disponíveis para execução

- `com.github.kyriosdata.rnds.ForcaConexao`  
  Força conexão com servidor definido no próprio código. Se
  não for possível conexão, uma exceção é gerada.

- `com.github.kyriosdata.rnds.BuscaPacientes`  
  Verifica se há pelo menos um paciente no servidor consultado.

### Links

- [keytool](https://docs.oracle.com/javase/10/tools/keytool.htm#JSWOR-GUID-5990A2E4-78E3-47B7-AE75-6D1826259549)
- Autenticação com certificado em [Java](https://stackoverflow.com/questions/1666052/java-https-client-certificate-authentication)
