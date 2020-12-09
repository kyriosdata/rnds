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
            <div class="col">
              <h1 className="hero__title titulo">
                Rede Nacional de Dados em Saúde ({siteConfig.title})
              </h1>
              <p className="subtitulo">
                O Guia de Integração RNDS orienta o desenvolvimento de solução
                tecnológica para envio de informações essenciais da notificação
                obrigatória de resultados de exames (SARS-CoV-2), do Sumário de
                Alta Hospitalar (SA) e do Registro de Atendimento Clínico (RAC),
                dentre outros modelos de informação, ao Ministério da Saúde.</p>
                
                <p className="subtitulo">
                Cada modelo de informação é acompanhado do objetivo,
                passo a passo para o credenciamento no Portal de Serviços (gestor), bem como, 
                para a integração entre o sistema local e a RNDS (desenvolvedor), dentre outras
                informações relevantes.              
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
