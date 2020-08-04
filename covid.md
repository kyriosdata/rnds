## Detalhando resultado de exame de COVID-19

O resultado de exame de COVID é representado pelo recurso FHIR [Composition](https://www.hl7.org/fhir/composition.html) personalizado
pela RNDS no perfil [Resultado de Exame Laboratorial](https://simplifier.net/redenacionaldedadosemsade/brresultadoexamelaboratorial-duplicate-3).
Este resultado compreende uma única seção definida pelo [Diagnóstico em Laboratório Clínico](https://simplifier.net/RedeNacionaldeDadosemSade/BRDiagnosticoLaboratorioClinico).

### Campos do resultado de exame laboratorial

Um resultado de exame laboratorial de COVID-19 é detalhado pelos campos: (a) status; (b) type; (c) subject; (d) date; (e) autor; (f) title e (g) section. 
Cada um deles detalhado abaixo.

- **status**: valor fixo "final". 
- **type**
  - **coding**
    - **system**: valor fixo "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoDocumento". Identifica um conjunto de códigos (_code system_) para [Tipo de Documento](https://simplifier.net/redenacionaldedadosemsade/brtipodocumento).
    - **code**: valor fixo "REL"
- **subject**: o sujeito da composição, ou seja, a quem se refere o resultado do exame. 
  - **identifier**
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

Estes campos classificam o [Tipo de Amostra Biológica](https://simplifier.net/redenacionaldedadosemsade/brtipoamostragal) utilizada em exames de acordo com a terminologia Gerenciador de Ambiente Laboratorial (GAL).

