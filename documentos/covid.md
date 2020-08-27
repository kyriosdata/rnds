O objetivo deste documento é

> fornecer detalhes dos dados necessários para registrar o resultado de exame de COVID-19, conforme definido pela RNDS. Tais dados são registrados em um documento JSON, cujos campos
> são definidos pelo recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html).

Ou seja,

- Você saberá quais são os dados necessários para montar um resultado de exame de COVID-19.
- Você saberá como estes dados devem ser fornecidos no documento JSON exigido pela RNDS.
- Você será capaz de montar um documento JSON para refletir o resultado de um dado exame.

### Contexto

Exames são realizados e os laudos correspondentes são produzidos por profissionais de saúde. Os laudos são possivelmente registrados em um sistema de software
empregado pelo laboratório em questão.

Cabe ao profissional de TI do laboratório disponibilizar um software que executa as seguintes atividades para cada laudo produzido pelo laboratório:

- Extrair os dados necessários do laudo em questão.
- Compor o documento JSON correspondente conforme perfis FHIR definidos pela RNDS.
- Enviar o documento JSON criado no passo anterior para a RNDS.

O profissional de TI do laboratório terá que obter valores do sistema de software empregado pelo laboratório para compor um documento JSON correspondente. Os valores necessários estão identificados em uma [tabela](resultado-exame-form.md).

Cada laboratório tem autonomia e usa um sistema específico para registro dos
laudos que produz. Em consequência, também cabe ao laboratório a extração dos
dados necessários a serem enviados para a RNDS. A composição do documento JSON,
por outro lado, deve seguir
a cada laudo e [envio](enviar-exame-covid.md) cada um deles para a RNDS.

### Detalhando resultado de exame de COVID-19

O recurso FHIR [Bundle](https://www.hl7.org/fhir/bundle.html) é um contêiner para uma coleção de recursos FHIR. No caso específico do exame de COVID-19, por exemplo,
conforme definido pela RNDS, o [Bundle](https://www.hl7.org/fhir/bundle.html) empregado para registrar o resultado deste exame deve ser formado pelo recurso [Composition](https://www.hl7.org/fhir/composition.html). Este recurso referencia um [Observation](https://www.hl7.org/fhir/observation.html) que, por fim,
faz uso de um tercerio recurso FHIR, o [Specimen](https://www.hl7.org/fhir/specimen.html).

O FHIR define um conjunto significativo de mais de uma centena recursos a serem empregados em vários contextos. Acima citamos apenas quatro deles.
A adequação de um recurso a um contexto específico
é realizada por meio de um perfil.

A RNDS, para contemplar o cenário nacional, já definiu vários perfis. Dois deles são empregados no Bundle
utilizado para informar o resultado de um exame de COVID-19: (a) [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3) que personaliza o recurso FHIR [Composition](https://www.hl7.org/fhir/composition.html) e (b) [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico), que personaliza o recurso FHIR [Observation](https://www.hl7.org/fhir/observation.html).

O terceiro recurso empregado no registro de um resultado de exame de COVID-19, o recurso FHIR [Specimen](https://www.hl7.org/fhir/specimen.html), é empregado conforme definido, sem adaptação.

Nas quatro seções seguintes são detalhados os campos empregados por cada um dos recursos utilizados para a submissão do resultado de um exame
de COVID-19: (a) [Bundle](https://www.hl7.org/fhir/bundle.html); (b) [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3); (c) [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e (d) [Specimen](https://www.hl7.org/fhir/specimen.html).

### Campos do Bundle

O Bundle é um contêiner para recursos FHIR, ou seja, serve como "pacote" no qual outros recursos FHIR são fornecidos.
Os campos seguintes são empregados no exemplo utilizado para submissão de um resultado de exame de COVID-19:

1. **resourceType**: indica o tipo do recurso FHIR em questão, ou seja, `Bundle`.
1. **meta**: objeto cujo único atributo fornecido é **lastUpdated**, indicando a data da última alteração do recurso.
1. **identifier**: identificar único do recurso. Este identificador deve ser persistente (fixo), e não deve se alterar, mesmo quando o recurso é
   transferido de um servidor para outro. Este objeto possui vários campos, dois deles são utilizados:

   - **system** é definido pela sequencia no seguinte formato: `http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-<laboratorioID>`, onde `laboratorioID` é o identificador do laboratório em questão fornecido pela RNDS no momento do cadastro. Este valor é aquele informado pelo Portal de Serviços, conforme ilustrado na figura abaixo. Observe que os números foram cobertos para anonimizar o cenário em questão.

   ![image](https://user-images.githubusercontent.com/1735792/90821002-9eb30f80-e308-11ea-8636-58645a1fa3c2.png)

   - **value**: identificador único do _Bundle_ no contexto do laboratório. Ou seja, este é um identificador único válido para o laboratório
     unicamente identificar o recurso em questão.

1. **type**: cujo valor é `document` neste caso. Convém observar que existem vários outros tipos para este recurso, empregados em outros cenários.
1. **timestamp**: o instante em que o recurso (_Bundle_) foi montado, ou seja, algo como `2020-03-23T14:23:56.567-02:00`, por exemplo.
1. **entry**: é uma lista (_array_) de objetos. Cada objeto desta lista é um recurso que faz parte do "pacote". De fato,
   um _Bundle_ existe para reunir outros recursos. No caso do resultado de exame de COVID-19, são empregados três recursos, conforme mencionado anteriormente: (a) [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3); (b) [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e (c) [Specimen](https://www.hl7.org/fhir/specimen.html). Ou seja, a lista fornecida
   no campo **entry** contém três objetos, um para cada um destes recursos. Cada objeto é detalhado pelos seguintes campos:

   - **fullUrl**: referência absoluta para o recurso ou, como o caso do resultado de exame de COVID-19, o identificador do recurso empacotado pelo _Bundle_. Neste exemplo os três valores empregados são `urn:uuid:transient-0`, `urn:uuid:transient-1` e `urn:uuid:transient-2`.
   - **resource**: objeto que detalha o recurso que faz parte do "pacote" (_Bundle_). No caso em questão, é aqui que será fornecido o conteúdo propriamente dito de cada um dos três recuros que fazem parte do _Bundle_ que registra o resultado de exame de COVID-19. Estes três recursos são detalhados nas três seções seguintes.

### Campos do resultado de exame laboratorial

Um resultado de exame laboratorial de COVID-19 é definido pelo recurso
[Composition](https://www.hl7.org/fhir/composition.html), em particular,
adaptado pela RNDS por meio do perfil
[Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3). Os campos empregados são detalhados abaixo:

1. **resourceType**: definido com o valor `Composition`.
1. **meta**: objeto cujo único campo empregado é **profile**, um _array_ cuja
   única entrada é `http://www.saude.gov.br/fhir/r4/StructureDefinition/BRResultadoExameLaboratorial-1.0`, ou seja, o identificador único do perfil
   em questão.
1. **status**: definido com o valor fixo `final`.
1. **type** é um objeto JSON com um único campo, **coding**, que por sua vez, é definido por um objeto JSON com dois campos:
   - **system**: valor fixo `http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoDocumento`. Identifica um conjunto de códigos (_code system_) para [Tipo de Documento](https://simplifier.net/redenacionaldedadosemsade/brtipodocumento).
   - **code**: valor fixo `REL`.
1. **subject**: o sujeito da composição, ou seja, a quem se refere o resultado do exame. Este é definido por um objeto JSON com o campo **identifier** que, por sua
   vez, é um objeto JSON formado por dois campos:
   - **system**: valor fixo `http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0`. Observe que existe uma definição para [Indivíduo](https://simplifier.net/RedeNacionaldeDadosemSade/BRIndividuo).
   - **value**: CNS do sujeito do exame, ou seja, aquele do qual o exame foi realizado.
1. **date** - Data e hora em que o documento foi criado (ocorreu a autoria), por exemplo `2020-03-23T10:30:12.947-02:00`.

1. **author**: responsável pelo laudo.
   - **identifier**
     - **system**: valor fixo `http://www.saude.gov.br/fhir/r4/StructureDefinition/BRPessoaJuridicaProfissionalLiberal-1.0`. Pesso jurídica e profissional liberal é definido em perfil [próprio](https://simplifier.net/redenacionaldedadosemsade/brpessoajuridicaprofissionalliberal)
     - **value**: CNPJ ou CPF do responsável pelo laudo.
1. **title**: valor fixo `Resultado de Exame Laboratorial`.
1. **section**: é uma lista (_array_) que, no caso de exame laboratorial, deve possuir um único objeto (elemento). O perfil definido pela RNDS estabelece que um único campo deve ser utilizado por este objeto único, o campo **entry**. O campo **entry** também
   é uma lista (_array_) e a expectativa é que um único objeto (elemento) seja fornecido. Este objeto único deve fazer uso de um único campo **reference**. Este campo deve
   possuir como valor o identificador único, neste "pacote", do recurso que registra o
   o diagnóstico do resultado, ou seja, o [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico). Convém ressaltar que este recurso identificado é um dos três recursos utilizados no _Bundle_. Em consequência, o valor do campo **reference** deve coincidir com o valor do campo **fullUrl** correspondente ao recurso referenciado.
   - **entry**
     - **reference**: valor fixo `urn:uuid:transient-1`

### Campos do diagnóstio em laboratório clínico

O diagnóstio em laboratório clínico é representado pelo recurso FHIR [Observation](https://www.hl7.org/fhir/observation.html) personalizado
pela RNDS no perfil [Diagnóstio em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico). À semelhança do recurso detalhado na seção anterior, novamente temos uma sequência de campos que descrevem o presente recurso e o perfil correspondente definido pela RNDS:

1. **resourceType**: valor `Observation`.
1. **meta**: objeto cujo único campo empregado é **profile**, um _array_ cuja
   única entrada é `http://www.saude.gov.br/fhir/r4/StructureDefinition/BRDiagnosticoLaboratorioClinico-1.0`, ou seja, o identificador único do perfil
   em questão.
1. **status**: valor `final`.
1. **category**: lista (_array_) com um único objeto (elemento), cujo único campo
   é **coding**, uma lista (_array_). Esta lista também possui um único elemento (objeto), formado por dois campos:
   - **system** cujo valor é `http://www.saude.gov.br/fhir/r4/CodeSystem/BRSubgrupoTabelaSUS`. Este valor identifica unicamente um sistema de códigos criado pela RNDS. Este sistema, [Subgrupo da Tabela SUS](https://simplifier.net/redenacionaldedadosemsade/brsubgrupotabelasus), conforme a descrição fornecida, "é o segundo nível da hierarquia da Tabela SUS e seus códigos ajudam a classificar e localizar procedimentos."
   - **code**: valor `0214`. Este valor, conforme o [Subgrupo da Tabela SUS](https://simplifier.net/redenacionaldedadosemsade/brsubgrupotabelasus), refere-se a `Diagnóstico por teste rápido`.
1. **code** é um objeto que tem como propósito identificar unicamente o [exame](https://simplifier.net/RedeNacionaldeDadosemSade/BRNomeExame-1.0). Este objeto possui um único campo definido: **coding** que, por sua vez, é uma lista (_array_) com um único elemento (objeto). Este único elemento (objeto) possui dois campos:
   - **system**: cujo valor é `http://www.saude.gov.br/fhir/r4/CodeSystem/BRNomeExameLOINC`. Este valor é o identificador único de um sistema de códigos, criado pela RNDS, para [nomes de exames LOINC](https://simplifier.net/RedeNacionaldeDadosemSade/BRNomeExameCOVID19LOINC).
   - **code**: aqui deve ser fornecido o valor, conforme os [nomes de exames LOINC](https://simplifier.net/RedeNacionaldeDadosemSade/BRNomeExameCOVID19LOINC), conforme o valor fornecido para **system**, campo anterior. Um valor possível é `94547-7`, que correspondente a `SARS Coronavírus 2, [presença de] anticorpos IgG e IgM em soro ou plasma por imunoensaio`.

### Campos do tipo de amostra biológica

Estes campos classificam o [Tipo de Amostra Biológica](https://simplifier.net/redenacionaldedadosemsade/brtipoamostragal) utilizada em exames de acordo com a terminologia GAL (Gerenciador de Ambiente Laboratorial). Existem vários códigos para o [Tipo de Amostra Biológica](https://simplifier.net/redenacionaldedadosemsade/brtipoamostragal), dentre eles, **SECONF**, empregado para identificar "Secreção Orofaríngea e Nasofaríngea".
