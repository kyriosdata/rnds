## Autenticação via SSL em Java

Java apenas faz uso de comunicação via SSL quando a aplicação em questão "confia" 
no serviço com o qual está interagindo. A confiança é estabelecida por meio de um arquivo, o _keystore_. 

O _keystore_ deve conter os certificados das autoridades certificadoras cujos certificados 
por ela assinados são considerados confiáveis. Dito de outro forma,
a aplicação em Java estabelece uma relação de confiança apenas com certificados assinados por autoridades certificadoras
cujos certificados estão contidos no _keystore_. 

O efeito prático é que uma aplicação em Java só confiará no
certificado de um servidor da RNDS, se a autoridade certificadora que emitiu tal certificado estiver no
_keystore_ empregado pela aplicação. Ou seja, é necessário dizer em qual autoridade certificadora a sua
aplicação deve confiar. Quando registrar tal informação no _keystore_ da aplicação, então ela
passará a confiar nos certificados por ela emitidos. 

Caso contrário, o resultado provável da tentativa de acesso à RNDS por meio de Java será

```
javax.net.ssl.SSLHandshakeException: PKIX path building failed: 
sun.security.provider.certpath.SunCertPathBuilderException: 
unable to find valid certification path to requested target
```

Abaixo seguem os passos para evitar este problema a partir do projeto de segurança disponibilizado
pelo DATASUS.

### Certificado empregado pelo DATASUS

Abra o seu navegador e navegue até **ehr-auth-hmg.saude.gov.br**, se for o Chrome, clique no cadeado ao lado da URL e,
na sequência, na opção "Certificado (válido)", o resultado será algo parecido com a tela abaixo:

![image](https://user-images.githubusercontent.com/1735792/92937056-3cd65900-f421-11ea-8325-0a7cfa5794cd.png)

Nesta tela, observa-se que "Let's Encrypt Authority X3" é quem emitiu o certificado pertinente à página
exibida pelo navegador. Ou seja, Java precisa que o certificado de "Let's Encrypt Authority X3" seja
acrescentado ao _keystore_ para que a aplicação possa confiar no certificado da RNDS. 

Em tempo, 
[Let's Encrypt](https://letsencrypt.org/) é uma autoridade certificadora sem fins lucrativos.
E, adicionalmente, os certificados por ela empregados, em particular o 
[Let's Encrypt Authority X3](https://letsencrypt.org/certificates/#intermediate-certificates)
pode ser baixado sem dificuldades.


Observe que a recíproca também é verdadeira. Em particular, a RNDS confia apenas em certificados
ICP-Brasil. 

### Baixar o certificado letsencrypt

No endereço https://letsencrypt.org/certificates/#intermediate-certificates é possível ter acesso a vários certificados. Um deles é o
_Let’s Encrypt Authority X3_. Um dos arquivos correspondentes é `letsencryptauthorityx3.der`, que está disponível [aqui](https://letsencrypt.org/certs/letsencryptauthorityx3.der).

### Obtenha o keystore do projeto de segurança

O arquivo **certificado.jks** é parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)). 
Este arquivo é um _keystore_ público, oferecido pelo DATASUS, cuja senha é "secret". O uso dele permite obter um _token_ de acesso, contudo, não
terá utilidade nos demais serviços (naturalmente). 

### Exibindo o conteúdo do keystore
Ao executar o comando `keytool -list -keystore certificado.jks` será exibido todo o conteúdo do _keystore_, no caso, contendo
três certificados, cujos nomes (_alias_) são _client_, _server_ e, o último, _*.saude.gov.br (geotrust rsa ca 2018)_, conforme abaixo.

```shell
Keystore type: PKCS12
Keystore provider: SUN

Your keystore contains 3 entries

client, 4 de mar de 2020, PrivateKeyEntry, 
Certificate fingerprint (SHA-256): 08:81:5D:4D:F5:86:77:31:63:15:A1:D3:C7:AA:4C:39:35:17:69:4C:62:24:86:19:33:88:0F:42:8D:04:D7:BA
server, 11 de set de 2020, trustedCertEntry, 
Certificate fingerprint (SHA-256): 1D:DB:8B:1C:A7:0C:C4:5D:5E:38:B5:09:ED:D2:F4:4C:1C:74:D8:65:BB:B6:AB:21:2D:AF:F1:58:08:74:99:CD
*.saude.gov.br (geotrust rsa ca 2018), 11 de set de 2020, trustedCertEntry, 
Certificate fingerprint (SHA-256): D7:6B:42:22:8D:BE:29:F3:00:5A:C6:A1:2D:2B:43:24:26:B9:3B:35:73:8E:61:CC:FD:31:8A:F7:1C:1E:F0:5C
```

Observe que nenhum destes certificado é da autoridade certificadora "Let's Encrypt Authority X3" e, possivelmente,
o uso deste _keystore_ pode falhar em Java. 

## Preparando o _keystore_

O arquivo **certificado.jks** é parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)). Este arquivo
contém o certificado da autoridade certificadora que assina os certificados dos serviços da RNDS.

Abaixo é ilustrada uma sequência de passos, e os comandos correspondentes, cuja execução extrai o certificado necessário do arquivo (**certificado.jks**) e o importa no arquivo do certificado digital empregado pelo laboratório para acesso à RNDS, ou o _keystore_ do laboratório. 
Para efeito destes passos é assumido que o certificado digital do laboratório está
contido no arquivo **laboratorio.pfx**. De fato, a sugestão é realizar uma cópia do certificado original para a realização dos passos abaixo. Em resumo, em um mesmo diretório estão os arquivos **certificado.pks** (oferecido pela RNDS) e uma cópia do certificado digital do laboratório (**laboratorio.pfx**).

A execução dos comandos abaixo exige a senha de acesso aos certificados. No caso do _keystore_ **certificado.jks**, a senha é **secret**. 
O certificado do cliente contido neste _keystore_ tem apenas a finalidade de teste. Por outro lado, a senha do _keystore_ do 
laboratório, definitivamente não é pública, e terá que ser conhecida por quem executar o comando. 

### Importar certificado

`keytool -importcert -file letsencryptauthorityx3.der -keystore certificado.jks -storepass secret -alias letsencrypt`

### Listar o conteúdo destes arquivos (opcional)

- `keytool -list -keystore certificado.jks`
- `keytool -list -keystore laboratorio.pfx`

### Exportar certificado da autoridade certificadora em certificado.jks

- `keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file ca.crt -keystore certificado.jks`

### Importar certificado da autoridade certificado obtido no passo anterior

- `keytool -import -trustcacerts -alias "saude.gov.br" -file ca.crt -keystore laboratorio.pfx`
