module.exports = {
  title: "Ferramentas",
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
      title: "Ferramentas",
      items: [
        {
          to: "docs/introducao",
          label: "Contexto",
          position: "left",
        },
        {
          to: "https://rnds-guia.saude.gov.br/docs/rel/objetivo-rel",
          label: "Modelos",
          position: "left",
        },
        {
          to: "https://rnds-guia.saude.gov.br/",
          label: "Guia de Integração",
          position: "left",
        },
      ],
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
