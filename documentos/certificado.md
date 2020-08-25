## Certificado digital

O arquivo **certificado.jks** e parte do projeto "Segurança - Projeto Java para gerar token autenticação", oferecido pela RNDS (baixe o arquivo [.zip](http://mobileapps.saude.gov.br/portal-servicos/files/f3bd659c8c8ae3ee966e575fde27eb58/53c86213276e091be7128abc031f5d38_8ymqlifr9.zip)).

### Conteúdo

`keytool -list -keystore certificado.pfx`

### Exportar certificado

`keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore cert.pfx`

### Importar certificado

`keytool -import -trustcacerts -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore cert.pfx`
