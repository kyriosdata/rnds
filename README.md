<img src="./media/guia.png" width="100px">

## Guia do Desenvolvedor RNDS

Interesse:

> Integração com a RNDS.

Avisos:

- Este **NÃO** é um portal do DATASUS/MS.
- Este **NÃO** é o portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br.
- Este portal **NÃO** está associado, não é mantido, não é vistoriado, não é acompanhado nem auditado pelo DATASUS ou pelo Ministério da Saúde.
- Este portal **NÃO** contém nenhuma informação privilegiada ou algo similar, ao contrário, tudo o que aqui está registrado pode ser encontrado na internet, sem restrição de acesso.

## Contexto

Em um cenário convencional, sem a introdução da RNDS, laudos produzidos permanecem restritos ao sistema de software do laboratório em questão.
Em algum momento são enviados para uma base de dados segura, e inacessível até aos pacientes.

A informação em saúde, contudo, "precisa chegar onde é necessária". A Portaria 1.792, de 17 de julho de 2020, do Ministério da Saúde,
é um marco histórico, neste sentido. Ela estabelece a obrigatoriedade de notificação de resultados de testes de SARS-CoV-2, conforme ilustrado abaixo, produzidos em todo o território nacional.

<img src="./media/laboratorio.png" width="600px">

Na prática, isso significa que laboratórios terão que produzir _software_ para integração com a RNDS, conforme ilustrado abaixo.

<img src="./media/pratica.png" width="500px">

O presente documento tem como compromisso identificar todos
os passos necessários, desde administrativos até técnicos, para realizar tal notificação, ou a _integração com a RNDS_, ou ainda, produzir um
_software_ que encaminha resultados de exames para a RNDS.

## Integração na perspectiva dos atores

A integração com a RNDS exige ações realizadas por dois atores: (a) o responsável pelo laboratório e (b) o responsável pela TI (Tecnologia
da Informação) do laboratório. O responsável pela TI pode ser um colaborador
do próprio laboratório ou serviço oferecido por empresa especializada,
dentre outros cenários.

### Atribuições do responsável pelo laboratório

1. Adquirir o certificado digital a ser utilizado para identificar o laboratório junto à RNDS. Este certificado é empregado no item seguinte (_solicitar acesso_) e também pelo _software_ de integração com a RNDS. O _software_ de integração é atribuição do responsável pela TI do laboratório (seção seguinte).
1. Solicitar acesso junto à RNDS. Esta solicitação é necessária para credenciamento do laboratório junto à RNDS. Este credenciamento dá origem
   ao **identificador do solicitante**, que será empregado pelo _software_ de integração. Esta solicitação é realizada com o apoio do responsável pela TI do laboratório, dado que depende de informações como faixa de IPs dos
   servidores empregados pelo laboratório e serviços a serem requisitados, dentre outros.
1. Esclarecer mapeamentos eventualmente necessários entre os dados produzidos por um laudo e aqueles esperados pela RNDS. O laboratório pode empregar uma terminologia ou códigos próprios para identificar os exames que realiza, enquanto a RNDS espera um código baseado no LOINC, por exemplo. Neste caso, cabe ao responsável pelo laboratório realizar o mapeamento entre os códigos que o laboratório faz uso e aqueles esperados pela RNDS.

### Atribuições do responsável pela TI do laboratório

1. Propor e desenvolver _software_ de integração. A proposta depende do contexto em questão, contudo, invariavelmente, terá que realizar funções bem-definidas.
1. Homologar. O _software_ deverá ser experimentado no ambiente de homologação. Esta experimentação deverá gerar evidências de que se integra satisfatoriamente à RNDS.
1. Colocar em produção. Algumas configurações são alteradas, como os
   endereços dos serviços do ambiente de produção.

- extrair dados do sistema de software empregado pelo laboratório;
- mapear código empregados pelo laboratório e/ou transformações de dados necessários para se adequar às exigências da RNDS;
- empacotar os dados na representação a ser utilizada para envio (JSON) e
- enviar os dados para a RNDS.

A figura ilustra as atribuições destes dois atores.

<img src="./media/desenvolvedor.png" width="600xp">

## Outros

- [Tecnologias](documentos/tecnologias.md)
- Fluxo:
  - Administrador
    - Certificado
    - Cadastro no Portal de Serviços
  - Ambiente de desenvolvimento
    - Postman
    - Java
  - Aplicações
    - Obter token
    - Consultar ...
