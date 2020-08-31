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
é um marco histórico, neste sentido. Ela estabelece a obrigatoriedade de notificar o Ministério da Saúde acerca dos resultados de testes de SARS-CoV-2, conforme ilustrado abaixo, produzidos em todo o território nacional.

<img src="./media/laboratorio.png" width="600px">

Na prática isso significa que cada laboratório terá que produzir _software_ para a sua integração com a RNDS. Noutras palavras, _software_ que notifica o Ministério da Saúde de cada resultado de exame que produz, conforme ilustrado abaixo.

<img src="./media/pratica.png" width="500px">

> O presente documento tem como compromisso identificar todos
> os passos necessários, administrativos e técnicos, para a _integração com a RNDS_.

## Software de Integração

O _software_ de integração com a RNDS é específico para esta finalidade e, portanto, demanda esforço correspondente. As duas seções seguintes
apresentam este _software_ na perspectiva dos principais atores e das
funções que deve implementar.

### Software de integração (atores)

A integração com a RNDS exige ações realizadas por dois atores: (a) o responsável pelo laboratório e (b) o responsável pela TI (Tecnologia
da Informação) do laboratório. O responsável pela TI pode ser um colaborador
do próprio laboratório ou serviço oferecido por empresa especializada,
dentre outros cenários. A figura abaixo compila as atribuições de ambos.

<img src="./media/papeis.png" width="500px">

#### Atribuições do responsável pelo laboratório

1. **Certificado digital**. Adquirir o certificado digital a ser utilizado para identificar o laboratório junto à RNDS. Este certificado é empregado no item seguinte (_solicitar acesso_) e também pelo _software_ de integração com a RNDS. O _software_ de integração é atribuição do responsável pela TI do laboratório (seção seguinte).
1. **Solicitar acesso**. Credenciamento junto à RNDS. Esta solicitação é necessária para credenciamento do laboratório junto à RNDS. Este credenciamento dá origem
   ao **identificador do solicitante**, que será empregado pelo _software_ de integração. Esta solicitação é realizada com o apoio do responsável pela TI do laboratório, dado que depende de informações como faixa de IPs dos
   servidores empregados pelo laboratório e serviços a serem requisitados, dentre outros.
1. **Validar mapeamentos**. Dados produzidos por um laudo devem ser mapeados para aqueles esperados pela RNDS, e o responsável pelo laboratório deve validar os mapeamentos. O laboratório pode empregar uma terminologia ou códigos próprios para identificar os exames que realiza, enquanto a RNDS espera um código baseado no LOINC, por exemplo. Neste caso, cabe ao responsável pelo laboratório realizar o mapeamento entre os códigos que o laboratório faz uso e aqueles esperados pela RNDS.

#### Atribuições do responsável pela TI do laboratório

1. **Desenvolver**. A proposta e desenvolvimento do _software_ de integração depende do contexto em questão, contudo, invariavelmente, terá que realizar funções bem-definidas. Tais funções incluem o padrão estabelecido pela RNDS para integração (FHIR).
1. **Homologar**. O _software_ deverá ser experimentado no ambiente de homologação. Esta experimentação deverá gerar evidências de que se integra satisfatoriamente à RNDS.
1. **Colocar em produção**. Algumas configurações são alteradas, como os
   endereços dos serviços do ambiente de produção.

### Software de integração (funções)

O _software_ de integração com a RNDS, a ser desenvolvido por cada laboratório, deve desempenhar um conjunto de funções bem-definidas.
As funções, os fluxos entre elas e os dados necessários são exibidas abaixo.

<img src="./media/rnds-dfd.png" width="650px">

Cada função é definida e classificada quanto à fase em que é executada (preparação ou entrega).

- PREPARAÇÃO
  1.  **Filtrar**. Dentre os dados produzidos para um laudo, aqueles necessários são selecionados.
  1.  **Mapear**. código empregados pelo laboratório e/ou transformações de dados necessários para se adequar às exigências da RNDS;
  1.  **Empacotar** os dados na representação a ser utilizada para envio (JSON).
  1.  **Verificar** o documento JSON correspondente ao laudo a ser enviado.
- ENTREGA
  1.  **Autenticar**. Obter a chave de acesso aos serviços.
  1.  **Enviar**. Atividade que notifica o resultado de um exame, conforme
      padrões estabelecidos pela RNDS, ao Ministério da Saúde.

A figura abaixo ilustra a classificação das funções, a ordem de execução destas funções e os dois ambientes (de homologação e produção).
A fase de preparação reúne funções "locais" ao laboratório, não demandam interação com um dos ambientes da RNDS. Por outro lado, as funções da
fase de entrega, _Autenticar_ e _Enviar_, depende do acesso a um dos
ambientes da RNDS.

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
