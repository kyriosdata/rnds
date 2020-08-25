## Interação via SSL em Java

Java apenas faz uso de comunicação via SSL quando a aplicação em questão "confia" no serviço com o qual está interagindo. 
A confiança é estabelecida por meio de um arquivo, o _keystore_. Em particular, este arquivo deve conter os certificados
das autoridades certificadoras nas quais deve confiar. 

## Certificado digital

O arquivo **certificado.jks** e parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)). Este arquivo
contém os certificados necessários para ilustrar a obtenção do _token_ de segurança. 

Abaixo é ilustrada uma sequência de passos, e os comandos correspondentes, cuja execução extrai os certificados necessários deste arquivo (**certificado.jks**) e os importa no arquivo do certificado digital empregado pelo laboratório para acesso à RNDS. Para efeito destes passos é assumido que o certificado digital do laboratório está
contido no arquivo **cert.pfx**. De fato, a sugestão é realizar uma cópia do certificado original para a realização dos passos abaixo. Em resumo, em um mesmo diretório estão os arquivos **certificado.pks** (oferecido pela RNDS) e uma cópia do certificado digital do laboratório (**cert.pfx**).

### Listar o conteúdo destes arquivos (opcional)

- `keytool -list -keystore certificado.jks`
- `keytool -list -keystore cert.pfx`

### Exportar certificados de certificado.jks

- `keytool -export -alias "server" -file server.crt -keystore certificado.jks`
- `keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore certificado.jks`

### Importar certificados obtidos no passo anterior

- `keytool -import -trustcacerts -alias server -file server.crt -keystore cert.pfx`
- `keytool -import -trustcacerts -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore cert.pfx`
