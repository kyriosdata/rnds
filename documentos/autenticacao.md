## Obter token de acesso

O serviço de autenticação da RNDS (oferecido pela porta **Auth**) produz um _token_ de acesso empregado pelos demais serviços (oferecidos pela porta **EHR**). A autenticação é realizada com o emprego do certificado digital do laboratório.

Uma aplicação em Java faz uso de SSL apenas quando há confiança no serviço com o
qual está interagindo. A

### Java

- [keytool](https://docs.oracle.com/javase/10/tools/keytool.htm#JSWOR-GUID-5990A2E4-78E3-47B7-AE75-6D1826259549)

- https://stackoverflow.com/questions/1666052/java-https-client-certificate-authentication

### Javascript

- https://medium.com/@sevcsik/authentication-using-https-client-certificates-3c9d270e8326
