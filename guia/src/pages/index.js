import React from "react";
import clsx from "clsx";
import Layout from "@theme/Layout";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import useBaseUrl from "@docusaurus/useBaseUrl";
import styles from "./styles.module.css";

const features = [
  {
    title: "RNDS",
    imageUrl: "img/rnds-logo.png",
    description: (
      <>
        A Rede Nacional de Dados em Saúde (
        <a href="https://rnds.saude.gov.br/">RNDS</a>) é uma "plataforma
        nacional de integração de dados em saúde" visando a transformação
        digital da saúde no Brasil.
      </>
    ),
  },
  {
    title: "Foco no desenvolvedor",
    imageUrl: "img/desenvolvedor-guia.png",
    description: (
      <>
        O <i>Guia</i> é produzido com foco no desenvolvedor. A integração é
        vista como um projeto cujo principal artefato é o <i>software</i> que
        requisita serviços oferecidos pela RNDS.
      </>
    ),
  },
  {
    title: "Conteúdo",
    imageUrl: "img/conteudo-guia.png",
    description: (
      <>
        Contexto, sequência de passos, orientações, explanações, código,&nbsp;
        <i>scripts</i>, enfim, tudo o que é necessário para compreender e fazer
        funcionar a integração com a RNDS.
      </>
    ),
  },
];

function Feature({ imageUrl, title, description }) {
  const imgUrl = useBaseUrl(imageUrl);
  return (
    <div className={clsx("col col--4", styles.feature)}>
      {imgUrl && (
        <div className="text--center">
          <img className={styles.featureImage} src={imgUrl} alt={title} />
        </div>
      )}
      <h3>{title}</h3>
      <p>{description}</p>
    </div>
  );
}

function Home() {
  const context = useDocusaurusContext();
  const { siteConfig = {} } = context;
  return (
    <Layout
      title={`Guia ${siteConfig.title}`}
      description="Guia de Integração da RNDS"
    >
      <header className={clsx("hero hero--primary", styles.heroBanner)}>
        <div className="container">
          <div class="row">
            <div class="col col--6 text--left">
              <h1 className="hero__title titulo">
                Rede Nacional de Dados em Saúde ({siteConfig.title})
              </h1>
              <p className="hero__subtitle">{siteConfig.tagline}</p>
              <div>
                <Link
                  className={clsx(
                    "button button--outline button--secondary button--lg",
                    styles.getStarted
                  )}
                  to={useBaseUrl("docs/intro/passo-a-passo")}
                >
                  Passo a passo
                </Link>
              </div>
            </div>

            <div class="col col--4 text--left introducao">
              O Guia de Integração RNDS tem como objetivo orientar o
              desenvolvimento de solução tecnológica para a notificação
              obrigatória de resultados de exames (SARS-CoV-2) ao Ministério da
              Saúde.
            </div>
            <div class="col col--6"></div>
          </div>
        </div>
      </header>
      <main>
        {features && features.length > 0 && (
          <section className={styles.features}>
            <div className="container">
              <div className="row">
                {features.map((props, idx) => (
                  <Feature key={idx} {...props} />
                ))}
              </div>
            </div>
          </section>
        )}
      </main>
    </Layout>
  );
}

export default Home;
