## Interação via SSL em Java

Java apenas faz uso de comunicação via SSL quando a aplicação em questão "confia" no serviço com o qual está interagindo. 
A confiança é estabelecida por meio de um arquivo, o _keystore_. Em particular, este arquivo deve conter os certificados
das autoridades certificadoras cujos certificados por ela assinados são considerados confiáveis. Dito de outro forma,
a aplicação em Java estabelece uma relação de confiança apenas com certificados assinados por autoridades certificadoras
cujos certificados estão contidos no _keystore_. 

Se no _keystore_ empregado pela aplicação não está o certificado da autoridade certificadora que assina o certificado do
serviço com o qual está tentando interagir, a confiança não é estabelecida e a exceção abaixo é gerada:

```
javax.net.ssl.SSLHandshakeException: PKIX path building failed: 
sun.security.provider.certpath.SunCertPathBuilderException: 
unable to find valid certification path to requested target
```

Abaixo é ilustrado como acrescentar ao _keystore_ contendo o certificado digital de um laboratório, o certificado da autoridade
certificadora que assina os certificados empregados pelos serviços da RNDS e, dessa forma, estabelecer a confiança necessária
para comounicação via SSL por uma aplicação em Java.

## Preparando o _keystore_

O arquivo **certificado.jks** é parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)). Este arquivo
contém o certificado da autoridade certificadora que assina os certificados dos serviços da RNDS.

Abaixo é ilustrada uma sequência de passos, e os comandos correspondentes, cuja execução extrai o certificado necessário do arquivo (**certificado.jks**) e o importa no arquivo do certificado digital empregado pelo laboratório para acesso à RNDS, ou o _keystore_ do laboratório. 
Para efeito destes passos é assumido que o certificado digital do laboratório está
contido no arquivo **laboratorio.pfx**. De fato, a sugestão é realizar uma cópia do certificado original para a realização dos passos abaixo. Em resumo, em um mesmo diretório estão os arquivos **certificado.pks** (oferecido pela RNDS) e uma cópia do certificado digital do laboratório (**laboratorio.pfx**).

### Listar o conteúdo destes arquivos (opcional)

- `keytool -list -keystore certificado.jks`
- `keytool -list -keystore laboratorio.pfx`

### Exportar certificado da autoridade certificadora em certificado.jks

- `keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file ca.crt -keystore certificado.jks`

### Importar certificado da autoridade certificado obtido no passo anterior

- `keytool -import -trustcacerts -alias "saude.gov.br" -file ca.crt -keystore laboratorio.pfx`
