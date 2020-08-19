## Certificado digital

### Conteúdo

`keytool -list -keystore certificado.pfx -alias nome`

### Exportar chave pública

`keytool -export -keystore certificado.pfx -file chave-publica.cer -alias nome`
