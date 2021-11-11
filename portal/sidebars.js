module.exports = {
  contexto: [
    "introducao",
    "conector",
    {
      "Público-alvo": [
        "publico-alvo/publico-alvo",
        {
          Gestor: [
            "publico-alvo/gestor/gestor",
            "publico-alvo/gestor/certificado",
            "publico-alvo/gestor/gov.br",
            "publico-alvo/gestor/portal",
            "publico-alvo/gestor/identificador",
          ],
          Integrador: [
            "publico-alvo/ti/ti",
            "publico-alvo/ti/conhecer",
            "publico-alvo/ti/homologar",
            "publico-alvo/ti/producao",
            "publico-alvo/ti/requisicoes",
            "publico-alvo/ti/erros",
            "publico-alvo/ti/autenticacao",
          ],
        },
      ],
    },
    "passo-a-passo",
    {
      RNDS: [
        "rnds/padrao",
        "rnds/ambientes",
        "rnds/servicos",
        "rnds/definicoes",
        "rnds/tecnologias",
        {
          Ferramentas: [
            "rnds/tools/api",
            "rnds/tools/bibliotecas",
            "rnds/tools/fhirpath",
            "rnds/tools/keytool",
            "rnds/tools/validacao",
            "rnds/tools/fhirserver",
          ],
        },
      ],
    },
    "faq",
  ],
  modelos: [
    {
      "Resultado de Exame Laboratorial (REL)": [
        "rel/objetivo-rel",
        "rel/mi-rel",
        "rel/mc-rel",
      ],
    },
  ],
};
