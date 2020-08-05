O objetivo deste documento é

> fornecer detalhes dos itens de informação do Bundle empregado para registrar o resultado de exame de COVID-19, conforme definido pela RNDS.

## Detalhando resultado de exame de COVID-19

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
de COVID-19: (a) [Bundle](https://www.hl7.org/fhir/bundle.html); (b) [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3); (c) Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e (d) [Specimen](https://www.hl7.org/fhir/specimen.html).

### Campos do Bundle
O Bundle é um contêiner para recursos FHIR, ou seja, serve como "pacote" no qual outros recursos FHIR são fornecidos. 
Os campos seguintes são empregados no exemplo utilizado para submissão de um resultado de exame de COVID-19: (a) resourceType; (b) meta;
(c) identifier; (d) type; (e) timestamp e (f) entry. 

- **resourceType**: indica o tipo do recurso FHIR em questão, ou seja, "Bundle".
- **meta**: objeto cujo único atributo fornecido é **lastUpdated**, indicando a data da última alteração do recurso. 
- **identifier**: identificar único do recurso (_Bundle_), e persistente (fixo), que não se altera, mesmo quando o _Bundle_ é 
transferido de um servidor para outro. Este objeto possui vários campos, dois deles são utilizados:
  - **system** é definido pela sequencia no seguinte formato: `http://www.saude.gov.br/fhir/r4/NamingSystem/BRRNDS-<laboratorioID>`, onde `laboratorioID` é o identificador do laboratório em questão fornecido pela RNDS no momento do cadastro. 
  - **value**: identificador único do _Bundle_ no contexto do laboratório. Ou seja, este é um identificador único válido para o laboratório 
  unicamente identificar o recurso em questão.
- **type**: cujo valor é `document` neste caso. Convém observar que existem vários outros tipos para este recurso, empregados em outros cenários.
- **timestamp**: o instante em que o recurso (_Bundle_) foi montado, ou seja, algo como `2020-03-23T14:23:56.567-02:00`, por exemplo.  
- **entry**: objeto que é um _array_, ou seja, uma lista de objetos. Cada objeto desta lista é um recurso que faz parte do "pacote". De fato,
o recurso _Bundle_ existe para reunir estes outros recursos. No caso do resultado de exame de COVID-19, são empregados três recursos, conforme mencionado anteriormente: (a) [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3); (b) [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico) e (c) [Specimen](https://www.hl7.org/fhir/specimen.html).

### Campos do resultado de exame laboratorial

Um resultado de exame laboratorial de COVID-19 é detalhado pelos campos: (a) status; (b) type; (c) subject; (d) date; (e) autor; (f) title e (g) section. 
Cada um deles detalhado abaixo.

- **status**: definido com o valor fixo "final". 
- **type** é um objeto JSON com um único campo, **coding**, que por sua vez, é definido por um objeto JSON com dois campos:
  - **system**: valor fixo "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoDocumento". Identifica um conjunto de códigos (_code system_) para [Tipo de Documento](https://simplifier.net/redenacionaldedadosemsade/brtipodocumento).
  - **code**: valor fixo "REL"
- **subject**: o sujeito da composição, ou seja, a quem se refere o resultado do exame. Este é definido por um objeto JSON com o campo **identifier** que, por sua
vez, é um objeto JSON formado por dois campos:
  - **system**: valor fixo "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRIndividuo-1.0". Observe que existe uma definição para [Indivíduo](https://simplifier.net/RedeNacionaldeDadosemSade/BRIndividuo).
  - **value**: CNS ou CPF do sujeito da composição. 
- **date** - Data e hora em que o documento foi criado (ocorreu a autoria), por exemplo "2020-03-23T10:30:12.947-02:00".

- **author**: responsável pelo laudo. 
  - **identifier**
    - **system**: valor fixo "http://www.saude.gov.br/fhir/r4/StructureDefinition/BRPessoaJuridicaProfissionalLiberal-1.0". Pesso jurídica e profissional liberal é definido em perfil [próprio](https://simplifier.net/redenacionaldedadosemsade/brpessoajuridicaprofissionalliberal)
    - **value**: CNPJ ou CPF do responsável pelo laudo.
- **title**: valor fixo "Resultado de Exame Laboratorial"
- **section**
  - **entry**
    - **reference**: valor fixo "urn:uuid:transient-1"
    
### Campos do diagnóstio em laboratório clínico
O diagnóstio em laboratório clínico é representado pelo recurso FHIR [Observation](https://www.hl7.org/fhir/observation.html) personalizado
pela RNDS no perfil [Diagnóstio em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico).

### Campos do tipo de amostra biológica

Estes campos classificam o [Tipo de Amostra Biológica](https://simplifier.net/redenacionaldedadosemsade/brtipoamostragal) utilizada em exames de acordo com a terminologia Gerenciador de Ambiente Laboratorial (GAL). Existem vários códigos para o [Tipo de Amostra Biológica](https://simplifier.net/redenacionaldedadosemsade/brtipoamostragal), dentre eles, **SECONF**, empregado para identificar "Secreção Orofaríngea e Nasofaríngea".

