<img src="./media/guia.png" width="100px">

## Guia do Desenvolvedor RNDS

Objetivo:

> Usufruir dos serviços da RNDS.

Avisos:

- Este **NÃO** é um portal do DATASUS/MS.
- Este **NÃO** é o portal da RNDS/DATASUS. O portal da RNDS é http://rnds.saude.gov.br.
- Este portal **NÃO** está associado, não é mantido, não é vistoriado, não é acompanhado nem auditado pelo DATASUS ou pelo Ministério da Saúde.
- Este portal **NÃO** contém nenhuma informação privilegiada ou algo similar, ao contrário, tudo o que aqui está registrado pode ser encontrado na internet, sem restrição de acesso.

Se você é desenvolvedor e, por algum motivo, precisa escrever código para enviar
dados ou consultar informações fornecidas pela RNDS, então este portal é para você!

## Mapa de orientação

Em um cenário convencional, sem a introdução da RNDS, laudos produzidos permaneceriam restritos ao sistema de software do laboratório em questão.
Conforme ilustrado abaixo, em algum momento seriam enviados para uma ilha
privada, segura, inacessível até aos pacientes.

<img src="./media/laboratorio.png" width="600px">

A saúde, contudo, demanda mudança. O processo anterior, figura acima, deve ser ajustado para que laudos cruzem as fronteiras
dos laboratórios em que foram produzidos. A intenção é "fazer a informação
em saúde chegar onde ela é necessária". A RNDS é o meio adotado pelo Brasil.

A Portaria X é o gatilho da mudança e para atendê-la segue uma sequência de atividades. De acordo com esta portaria, os laboratórios continuam realizando suas atividades fins, como antes.
Contudo, induz a realização de um esforço de Integração com a RNDS.

## Integração com a RNDS

A integração com a RNDS exige ações realizadas por dois atores: (a) o responsável pelo laboratório e (b) o responsável pela produção de código (_software_).

É atribuição do responsável pelo laboratório:

- adquirir e disponibilizar o certificado digital a ser utilizado para identificar o laboratório junto à RNDS;
- solicitar acesso junto à RNDS e
- esclarecer mapeamentos e transformações eventualmente necessários entre os dados fornecidos para um laudo e aqueles esperados pela RNDS.

O responsável pela produção do código (_software_) que realiza a integração com a RNDS deverá assegurar a execução correta de quatro características do código a ser produzido:

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
