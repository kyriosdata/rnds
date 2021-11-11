module.exports = {
  title: "RNDS",
  tagline: "Guia de Integração",
  url: "https://kyriosdata.github.io",
  baseUrl: "/rnds/",
  onBrokenLinks: "throw",
  favicon: "img/favicon.ico",
  organizationName: "kyriosdata", // Usually your GitHub org/user name.
  projectName: "rnds", // Usually your repo name.
  themes: ["@docusaurus/theme-live-codeblock"],
  themeConfig: {
    navbar: {
      title: "Guia",
      items: [
        {
          to: "docs/rel/objetivo-rel",
          label: "Modelos",
          position: "left",
        },
        {
          to: "docs/introducao",
          label: "Contexto",
          position: "left",
        },
        {
          to: "https://servicos-datasus.saude.gov.br/",
          label: "Portal de Serviços",
          position: "left",
        },
      ],
    },

    algolia: {
      apiKey: "773e8639e2cf11a7a1b3e06912c0a294",
      indexName: "rnds",
      searchParameters: {}, // Optional (if provided by Algolia)
    },
  },
  presets: [
    [
      "@docusaurus/preset-classic",
      {
        docs: {
          sidebarPath: require.resolve("./sidebars.js"),
          // O comentário abaixo remove link "Edit this file"
          // editUrl: "https://github.com/kyriosdata/rnds/edit/master/guia/",
          // Exibe data em que página foi atualizada.
          showLastUpdateTime: true,
        },
        blog: {
          showReadingTime: true,
          // Please change this to your repo.
          editUrl: "https://github.com/kyriosdata/rnds/edit/master/guia/blog/",
        },
        theme: {
          customCss: require.resolve("./src/css/custom.css"),
        },
        sitemap: {
          changefreq: "weekly",
          priority: 0.5,
        },
      },
    ],
  ],
};
