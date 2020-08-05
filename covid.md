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

