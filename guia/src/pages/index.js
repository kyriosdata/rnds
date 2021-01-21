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
        O <i>Guia</i> é produzido com foco no integrador (desenvolvedor de
        software). A integração é vista como um projeto cujo principal artefato
        é o <i>software</i> que requisita serviços oferecidos pela RNDS.
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
            <div class="col">
              <h1 className="hero__title titulo">
                Rede Nacional de Dados em Saúde ({siteConfig.title})
              </h1>
              <p className="subtitulo">
                Este Guia de Integração RNDS orienta o desenvolvimento de
                solução tecnológica (conector) para interoperabilidade em saúde
                no Brasil.
              </p>

              <p className="subtitulo">
                O passo a passo apresenta uma sequência de atividades para a
                integração, distribuídas entre o gestor e o integrador
                (desenvolvedor de software). Cada necessidade de troca de
                informação é detalhada pelos modelos informacional e
                computacional, bem como outras orientações.
              </p>
              <div>
                <Link
                  className={clsx(
                    "button button--outline button--secondary button--lg",
                    styles.getStarted
                  )}
                  to={useBaseUrl("docs/rel/intro/passo-a-passo")}
                >
                  Passo a passo
                </Link>
              </div>
            </div>
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
