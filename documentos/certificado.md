## Certificado digital

### Conteúdo

`keytool -list -keystore certificado.pfx`

### Exportar chave pública

`keytool -export -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore cert.pfx`

### Importar certificado

`keytool -import -trustcacerts -alias "*.saude.gov.br (geotrust rsa ca 2018)" -file dominio.crt -keystore cert.pfx`
