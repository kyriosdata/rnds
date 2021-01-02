module.exports = {
  title: "RNDS",
  tagline: "Guia de Integração",
  url: "https://kyriosdata.github.io",
  baseUrl: "/rnds/",
  onBrokenLinks: "log",
  favicon: "img/favicon.ico",
  organizationName: "kyriosdata", // Usually your GitHub org/user name.
  projectName: "rnds", // Usually your repo name.
  themes: ["@docusaurus/theme-live-codeblock"],
  themeConfig: {
    navbar: {
      title: "Guia",
      logo: {
        alt: "Guia de Integração RNDS",
        src: "img/guia-logo.png",
      },
      items: [
        {
          to: "docs/introducao",
          activeBasePath: "docs",
          label: "Conteúdo",
          position: "left",
        },
        { to: "blog", label: "Notícias", position: "left" },
        {
          href: "https://github.com/kyriosdata/rnds",
          label: "GitHub",
          position: "right",
        },
      ],
    },
    footer: {
      style: "dark",
      links: [
        {
          title: "Tópicos",
          items: [
            {
              label: "Resultado de Exame Laboratorial",
              to: "docs/rel/intro/passo-a-passo",
            },
            {
              label: "Sumário de Alta (SA)",
              to: "docs/sa/objetivo-sa",
            },
            {
              label: "Registro de Atendimento Clínico (RAC)",
              to: "docs/rac/objetivo-rac",
            },
          ],
        },
        {
          title: "Comunidade",
          items: [
            {
              label: "GitHub",
              href: "https://github.com/kyriosdata/rnds",
            },
          ],
        },
        {
          title: "Outros",
          items: [
            {
              label: "Notícias",
              to: "blog",
            },
          ],
        },
      ],
      copyright: `Copyright © 2020 DATASUS (02/01/2021)`,
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
          // Please change this to your repo.
          editUrl: "https://github.com/kyriosdata/rnds/edit/master/guia/",
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
          cacheTime: 600 * 1000, // 600 sec - cache purge period
          changefreq: "weekly",
          priority: 0.5,
          trailingSlash: false,
        },
      },
    ],
  ],
};
