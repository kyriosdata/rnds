## Interação via SSL em Java

Java apenas faz uso de comunicação via SSL quando a aplicação em questão "confia" no serviço com o qual está interagindo. 
A confiança é estabelecida por meio de um arquivo, o _keystore_. Em particular, este arquivo deve conter os certificados
das autoridades certificadoras cujos certificados por ela assinados são considerados confiáveis. Dito de outro forma,
a aplicação em Java estabelece uma relação de confiança apenas com certificados assinados por autoridades certificadoras
cujos certificados estão contidos no _keystore_. 

Abaixo é ilustrado como acrescentar ao _keystore_ contendo o certificado digital de um laboratório, o certificado da autoridade
certificadora que assina os certificados empregados pelos serviços da RNDS.

## Certificado digital

O arquivo **certificado.jks** e parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)). Este arquivo
contém os certificados necessários para ilustrar a obtenção do _token_ de segurança. 

Abaixo é ilustrada uma sequência de passos, e os comandos correspondentes, cuja execução extrai os certificados necessários deste arquivo (**certificado.jks**) e os importa no arquivo do certificado digital empregado pelo laboratório para acesso à RNDS. Para efeito destes passos é assumido que o certificado digital do laboratório está
contido no arquivo **cert.pfx**. De fato, a sugestão é realizar uma cópia do certificado original para a realização dos passos abaixo. Em resumo, em um mesmo diretório estão os arquivos **certificado.pks** (oferecido pela RNDS) e uma cópia do certificado digital do laboratório (**cert.pfx**).

### Listar o conteúdo destes arquivos (opcional)

- `keytool -list -keystore certificado.jks`
- `keytool -list -keystore cert.pfx`

### Exportar certificado da autoridade certificadora em certificado.jks

- `keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file ca.crt -keystore certificado.jks`

### Importar certificado da autoridade certificado obtido no passo anterior

- `keytool -import -trustcacerts -alias "saude.gov.br" -file ca.crt -keystore cert.pfx`
