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
          to: "docs/roteiro",
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
              label: "Passo a passo",
              to: "docs/roteiro",
            },
            {
              label: "Contexto",
              to: "docs/contexto",
            },
          ],
        },
        {
          title: "Comunidade",
          items: [
            {
              label: "Stack Overflow",
              href: "https://stackoverflow.com/questions/tagged/docusaurus",
            },
            {
              label: "Discord",
              href: "https://discordapp.com/invite/docusaurus",
            },
            {
              label: "Twitter",
              href: "https://twitter.com/docusaurus",
            },
          ],
        },
        {
          title: "Outros",
          items: [
            {
              label: "Blog",
              to: "blog",
            },
            {
              label: "GitHub",
              href: "https://github.com/kyriosdata/rnds",
            },
          ],
        },
      ],
      copyright: `Copyright © ${new Date().getFullYear()} Engenharia de Software (UFG).`,
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
      },
    ],
  ],
};
