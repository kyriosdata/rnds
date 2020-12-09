---
id: mi-rel
title: Modelo de Informação
sidebar_label: Modelo de Informação
---

## Contexto

Em dezembro de 2019, na província de Wuhan (China), foi identificada uma nova cepa de coronavírus, reportada por causar sintomas de pneumonia em humanos e cuja origem permanece desconhecida até o momento (1).

Este vírus, denominado SARS CoV2, mostrou-se ser altamente transmissível, e registros da sua presença foram crescentes pelo mundo até a declaração de pandemia mundial pela Organização Mundial da Saúde (OMS) em 11/03/2020 (2).

COVID-19 é a denominação da doença causada pelo SARS CoV-2 (3), caracterizada por uma infecção respiratória que pode ser assintomática, com sintomas leves a moderados, mas também causar síndrome respiratória aguda grave e insuficiência renal em casos graves e críticos. Esta doença tem mobilizado autoridades mundiais na prevenção de sua transmissibilidade, na otimização da assistência à saúde e na melhoria da gestão e coordenação dos serviços, de modo a evitar um colapso dos sistemas de saúde e permitir acesso dos indivíduos ao cuidado assistencial.

Considerando a [Portaria nº 1.792/GM/MS](https://www.in.gov.br/en/web/dou/-/portaria-n-1.792-de-17-de-julho-de-2020-267730859), de 17 de julho de 2020 que altera a Portaria nº 356/GM/MS, de 11 de março de 2020, para dispor sobre a obrigatoriedade de notificação ao Ministério da Saúde de todos os resultados de testes diagnóstico para SARS-CoV-2 realizados por laboratórios da rede pública, rede privada, universitários e quaisquer outros, em todo território nacional tornando obrigatório aos laboratórios clínicos enviarem os resultados dos exames para detecção da COVID-19 ao Ministério da Saúde, por meio da Rede Nacional de Dados em Saúde (RNDS).

A Rede Nacional de Dados em Saúde (RNDS) vem ao encontro desta questão, contribuindo tanto na continuidade do cuidado em saúde, pela disponibilização da informação em plataformas de acesso direto pelo cidadão e profissionais de saúde (uso primário), quanto como uma fonte de informação para uso secundário dos dados, que pode subsidiar bases de dados de vigilância epidemiológica do Ministério da Saúde e Secretarias Estaduais e Municipais de Saúde, além de munir os gestores do Brasil com informações relevantes ao enfrentamento à pandemia.

Trata-se de uma plataforma nacional de integração de dados em saúde, sendo um projeto estruturante do Conecte SUS, programa do Governo Federal para a transformação digital da saúde no Brasil. Contém um repositório de documentos responsável por armazenar informações de saúde dos cidadãos, mantendo a privacidade, integridade e auditabilidade dos dados e promovendo a acessibilidade e interoperabilidade das informações de forma segura e controlada (4).

## Escopo

A versão da proposta de modelo de informação descrita neste documento considera o registro de resultados de exame para COVID-19 na Rede Nacional de Dados de Saúde (RNDS).

O modelo de informação é uma representação conceitual e canônica, na qual os elementos referentes a um documento específico são modelados em seções e blocos de dados, com seus respectivos tipos de dados a serem informados. Também são informadas as referências para o uso de recursos terminológicos. A tabela 1 apresenta os elementos que fazem parte do modelo de informação onde:

- Coluna 1 - Nível: apresenta o nível do elemento no modelo de informação;

- Coluna 2 - Ocorrência: descreve o número de vezes que o elemento deve/pode aparecer. Assim:

  - [0..1] - o elemento é opcional e, se ocorrer, aparecer uma vez;
  - [1..1] - o elemento é obrigatório e deve estar presente uma única vez;
  - [0..N] - o elemento é opcional e pode ocorrer várias vezes;
  - [1..N] - o elemento é obrigatório e pode ocorrer várias vezes;

- Coluna 3 - Elemento: apresenta o elemento a ser informado;

- Coluna 4 - Descrição/Regras: apresenta o conceito e ou a regra referente ao elemento;

- Coluna 5 - Tipo de dado: descreve o tipo de dado a ser preenchido, e

- Coluna 6 - Dado: apresenta o(s) dado(s) possível(is), conforme o tipo de dado descrito na coluna anterior.

Tabela 1 - Modelo de informação do Resultado de Exame Laboratorial COVID-19

| Nível | Ocorrência | Elemento                          | Descrição/Regras                                                                                                                                                      | Tipo             | Dado                                     |
| ----- | ---------- | --------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ---------------------------------------- |
| 1     | 1..1       | Laboratório                       |                                                                                                                                                                       |                  |                                          |
| 2     | 1..1       | Nome do laboratório               | Nome do estabelecimento de saúde responsável pelo resultado do exame laboratorial.                                                                                    | Texto            |                                          |
| 2     | 1..1       | CNES                              | Número do Cadastro Nacional do Estabelecimentos de Saúde do laboratório responsável pelo resultado do exame laboratorial.                                             | Texto            |                                          |
| 2     | 0..1       | CNPJ                              |                                                                                                                                                                       | Texto            |                                          |
| 2     | 0..1       | Responsável técnico               |                                                                                                                                                                       |                  |                                          |
| 3     | 1..1       | Nome completo do profissional     | Nome completo do responsável técnico pelo laboratório.                                                                                                                | Texto            |                                          |
| 3     | 1..1       | Conselho de Classe Profissional   |                                                                                                                                                                       |                  |                                          |
| 4     | 1..1       | Tipo de conselho                  | Conselho de classe profissional do responsável técnico pelo laboratório.                                                                                              | Texto codificado | CRM, CRF, CRBM, CRBIO, CRQ               |
| 4     | 1..1       | Unidade Federativa                | Unidade Federativa do conselho de classe profissional do responsável técnico pelo laboratório.                                                                        | Texto            |                                          |
| 4     | 1..1       | Número do registro                | Número do registro no conselho de classe profissional do responsável técnico pelo laboratório.                                                                        | Texto            |                                          |
| 1     | 1..1       | Identificação do Indivíduo        |                                                                                                                                                                       |                  |                                          |
| 2     | 1..1       | Nome completo                     | Nome completo do sujeito do exame laboratorial                                                                                                                        | Texto            |                                          |
| 2     | 1..1       | CNS                               | Número do Cartão Nacional de Saúde válido                                                                                                                             | Texto            |                                          |
| 1     | 1..1       | Resultado de exame de laboratório |                                                                                                                                                                       |                  |                                          |
| 2     | 1..N       | Nome do exame                     | Nome do exame a que foi submetida a amostra biológica. Terminologia externa LOINC, termos prerelease COVID 19.                                                        | Texto codificado | LOINC                                    |
| 3     | 0..1       | Resultado qualitativo             | Valor atribuído ao analito de acordo com o método de análise, de forma qualitativa.                                                                                   | Texto codificado | Detectável; Não detectável; Inconclusivo |
| 3     | 0..1       | Resultado quantitativo            | Valor quantitativo do resultado do exame, expresso com unidades de medida                                                                                             | Quantidade       |                                          |
| 3     | 1..1       | Amostra                           | Amostra biológica, preparada ou não, que foi submetida ao exame laboratorial. Ex: "soro", "plasma", "sangue". Terminologias externas FHIR v2-0487 e Tipo Amostra GAL. | Texto codificado | FHIR v2-0487 ou Tipo Amostra GAL         |
| 3     | 1..1       | Método de análise                 | Método analítico utilizado para determinação do resultado do analito.                                                                                                 | Texto            |                                          |
| 3     | 1..1       | Faixa de referência               | Faixa de valores de resultado esperada para determinada população de indivíduos.                                                                                      | Texto            |                                          |
| 3     | 1..1       | Data hora do resultado            | Data hora na qual o resultado do exame laboratorial foi registrado.                                                                                                   | Data/Hora        |                                          |
| 3     | 0..N       | Nota                              | Narrativa adicional sobre o exame laboratorial.                                                                                                                       | Texto            |                                          |
