## Postman (Error: certificate has expired)

Conforme ilustrado abaixo, observe que a opção **SSL certificate verification** está desligada (**OFF**). 
Desta forma, o Postman não verifica a integridade do certificado, e ignora o fato do certificado 
empregado pelo DATASUS estar vencido. Se esta opção não for desligada, não será possível efetuar 
requisições com o atual certificado do DATASUS. 

![img](../../static/img/postman-certificado.png)
