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
        nacional de integração de dados em saúde" visando a{" "}
        <i>transformação digital da saúde no Brasil</i>.
      </>
    ),
  },
  {
    title: "Foco no integrador",
    imageUrl: "img/integrador.png",
    description: (
      <>
        O <i>Guia</i> é produzido com foco no <i>integrador</i> (desenvolvedor
        de software). O principal artefato é o software que requisita serviços
        oferecidos pela RNDS.
      </>
    ),
  },
  {
    title: "Conteúdo",
    imageUrl: "img/conteudo.png",
    description: (
      <>
        O <i>Guia</i> inclui contexto, passos, orientações, código, enfim, o
        conteúdo suficiente para compreender e colocar em funcionamento a
        integração com a RNDS.
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
              <p className="hero__subtitle">
                Este Guia orienta estabelecimentos de saúde acerca da
                <b> integração com a RNDS</b>, visando a interoperabilidade para
                os cuidados em saúde no Brasil.
              </p>
              <div>
                <Link
                  className={clsx(
                    "button button--outline button--secondary button--lg",
                    styles.getStarted
                  )}
                  to={useBaseUrl("docs/passo-a-passo")}
                >
                  Passo a passo
                </Link>
              </div>
            </div>
          </div>
        </div>
      </header>
      <main>
        <div className="destaque">
          <div className="container padding-vert--md">
            <div className="row">
              <div className="col col--10 col--offset-1">
                A <b>integração com a RNDS</b> é apresentada na forma de uma
                sequência de atividades (passo a passo), que envolve os atores
                (papéis) Gestor de Saúde (responsável pelo estabelecimento de
                saúde) e Integrador (desenvolvedor de software). A{" "}
                <b>integração com a RNDS </b>
                promove a troca sistemática de informação em saúde, que é
                descrita aqui pelo Modelo Informacional e pelo Modelo
                Computacional, acompanhados de outras orientações.
              </div>
            </div>
          </div>
        </div>
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
