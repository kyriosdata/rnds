# Biblioteca

Biblioteca que encapsula requisições submetidas para a RNDS por meio de uma interface em Java.

### Maven

```xml
<dependency>
  <groupId>com.github.kyriosdata</groupId>
  <artifactId>rnds-lib</artifactId>
  <version>0.0.4</version>
</dependency>
```

### Código

```java
RNDS rnds = new RNDSBuilder().build();
final RNDS.Resposta resposta = rnds.cnes("2337991");
// "resposta" conterá o resultado da requisição, neste caso, busca por CNES.
// resposta.code (código HTTP resposta)
// resposta.retorno (payload) e
// resposta.headers (headers retornados). 
```

### Links

- [keytool](https://docs.oracle.com/javase/10/tools/keytool.htm#JSWOR-GUID-5990A2E4-78E3-47B7-AE75-6D1826259549)
- Autenticação com certificado em [Java](https://stackoverflow.com/questions/1666052/java-https-client-certificate-authentication)

## Autenticação SSL em Java
Java apenas usa comunicação via SSL quando a aplicação em questão 
"confia" no serviço com o qual interage. A confiança é estabelecida 
por meio do conteúdo de um arquivo, o keystore.

O keystore deve conter os certificados das autoridades certificadoras 
cujos certificados por ela assinados são considerados confiáveis. Dito 
de outra forma, a aplicação em Java estabelece uma relação de confiança 
apenas com certificados assinados por autoridades certificadoras cujos 
certificados estão contidos no keystore.

O efeito prático é que uma aplicação em Java só confiará no certificado 
de um servidor da RNDS, se a autoridade certificadora que emitiu tal 
certificado estiver no keystore empregado pela aplicação. Ou seja, 
é necessário dizer em qual autoridade certificadora a sua aplicação 
deve confiar. Quando registrar tal informação no keystore da aplicação, 
então ela passará a confiar nos certificados por ela emitidos.

Caso contrário, o resultado provável da tentativa de acesso à RNDS 
terá como resposta a exceção abaixo:

```
javax.net.ssl.SSLHandshakeException: PKIX path building failed:
sun.security.provider.certpath.SunCertPathBuilderException:
unable to find valid certification path to requested target
```

## Preparação para autenticação SSL em Java

Em Java é preciso criar um arquivo, _keystore_, onde os certificados relevantes são armazenadas.
Neste caso, são necessários dois certificados: 

- Certificado da Autoridade Certificadora 
- Certificado do Estabelecimento de Saúde 

### Certificado da Autoridade Certificadora 

Em Java é preciso confiar em quem emite o certificado com quem se deseja interagir de 
forma confiável. 

Abra o seu navegador e navegue até **ehr-auth-hmg.saude.gov.br**, 
por conveniência clique https://ehr-auth-hmg.saude.gov.br. 
Ignore a resposta do navegador. Se for o Chrome, clique no cadeado ao lado da URL e, 
na sequência, na opção "Certificado (válido)", depois aba "Detalhes". 
Selecione "Emissor". O emissor, neste caso, é quem emitiu o certificado empregado
pela RNDS, e nossa aplicação em Java precisa confiar nele.

Clique no botão "Copiar para Arquivo..." para exportar o certificado do emissor.
Selecione a opção "X509 binário codificado por DER (.cer)". O arquivo 
criado identifica a autoridade certificadora. Este arquivo, por exemplo,
**chave-publica.cer**, será empregado nos passos abaixo. 

o resultado será algo parecido com a tela abaixo:

### Certificado do Estabelecimento de Saúde

O certificado do estabelecimento de saúde é um arquivo .pfx, que também será 
empregado nos passos abaixo.

### Criar arquivo _keystore_

**Importante**: o _keystore_ criado fez uso da mesma senha do certificado do estabelecimento.

- Criar _keystore_ denominado **rnds.pfx** (uma chave arbitrária é gerada)  
`>keytool -genkey -alias main -keystore rnds.pfx -storetype PKCS12 -keyalg RSA -storepass 123456`

- Verifique o conteúdo do _keystore_ (estará vazio)    
`keytool -list -keystore rnds.pfx` (senha '123456')
  
- Importe o certificado do estabelecimento de saúde  
`keytool -importkeystore -srckeystore estabelecimento.pfx -srcstoretype PKCS12 -destkeystore rnds.pfx -deststoretype PKCS12`

- Importe o certificado da autoridade certificadora (CA)  
`keytool -import -noprompt -trustcacerts -file Root-R3.crt -keystore rnds.pfx -storepass 123456`

### Uso do arquivo _keystore_

- O arquivo _keystore_ criado no passo anterior deve ser indicado
por meio da variável de ambiente **RNDS_CERTIFICADO_ENDERECO**. 
  
- A senha empregada para criar o arquivo, a sugestão foi empregar a
mesma senha do certificado do estabelecimento, deverá ser informada 
pela variável **RNDS_CERTIFICADO_SENHA**.
  
- O endereço para autenticação via SSL deve ser fornecido pela 
variável **RNDS_AUTH**.

#### Outras operações que podem ser úties

- Repositório do JDK/JRE (lib/security/cacerts) (senha padrão é "changeit")
  - `keytool -list -cacerts` (listar conteúdo de cacerts)
  - `keytool -list -cacerts -alias rnp` (exibir detalhes de chave específica)
  - `keytool -delete -alias rnp -cacerts` (remove certificado identificado por "rnp") 
    
- Arquivo _keystore_ 
  - `keytool -delete -alias rnp -keystore rnds.jks` (remove certificado "rnp")
    
  - `keytool -list -keystore rnds` (exibe conteúdo)
  - `keytool -import -alias rnp -keystore rnds.jks -file rnp-chave-publica.cer`
    
  - `keytool -storepasswd -keystore rnds.jks` (altera senha do _keystore_) 

## Como obter a chave pública empregada pelo servidor da RNDS?

Conforme o ambiente cuja chave pública é desejada, use um dos comandos abaixo:
- `openssl s_client -connect ehr-auth-hmg.saude.gov.br:443` (ambiente de homologação)
- `openssl s_client -connect ehr-auth.saude.gov.br:443` (ambiente de produção)

Parte da saída incluirá a chave pública do servidor. Copia o trecho que se inicia
com `-----BEGIN CERTIFICATE-----` e termina com 
`-----END CERTIFICATE-----`. Este é o certificado que contém a chave pública.
Salve este conteúdo (texto) em um arquivo, por exemplo, **rnds-chave-publica-producao.pem**
para o ambiente de produção. 

Ao executar o comando abaixo

- `openssl x509 -pubkey -in rnds-chave-publica-producao.pem -noout`

será fornecida na saída padrão a chave pública empregada pelo servidor da RNDS 
do ambiente de produção. 

