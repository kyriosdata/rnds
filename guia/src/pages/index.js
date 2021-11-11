import React from "react";
import clsx from "clsx";
import Layout from "@theme/Layout";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import useBaseUrl from "@docusaurus/useBaseUrl";
import styles from "./styles.module.css";

import rndsImg from "@site/static/img/rnds-logo.png";
import integradorImg from "@site/static/img/integrador.png";
import conteudoImg from "@site/static/img/conteudo.png";
import cgisImg from "@site/static/img/cgis.png";
import datasusImg from "@site/static/img/datasus.png";
import msImg from "@site/static/img/ms.png";

const features = [
  {
    title: "RNDS",
    imageUrl: rndsImg,
    description: (
      <>
        A Rede Nacional de Dados em Saúde (
        <a href="https://rnds.saude.gov.br/">RNDS</a>) é uma "plataforma
        nacional de integração de dados em saúde" visando a{" "}
        <i>transformação digital</i> da saúde no Brasil.
      </>
    ),
  },
  {
    title: "Guia",
    imageUrl: integradorImg,
    description: (
      <>
        Este <i>Guia</i> inclui orientações para gestores e integradores, além
        de modelos de informação e computacional correspondentes a cada troca de
        informação em saúde com a RNDS.
      </>
    ),
  },
  {
    title: "Curso",
    imageUrl: conteudoImg,
    description: (
      <>
        Se você é desenvolvedor de software e está interessado na integração com
        a RNDS, então consulte o{" "}
        <a
          href="https://www.unasus.gov.br/cursos/oferta/418988"
          target="_blank"
        >
          curso
        </a>{" "}
        gratuito e online para detalhes.
      </>
    ),
  },
];

function Feature({ imageUrl, title, description }) {
  const imgUrl = imageUrl;
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
      class="meuleiaute"
      title={`${siteConfig.title}`}
      description="Software para Integração com a RNDS"
    >
      <header className={clsx("hero hero--primary", styles.heroBanner)}>
        <div className="container">
          <div class="row">
            <div class="col">
              <h1 className="hero__title titulo">
                Ferramentas de integração com a RNDS
              </h1>
              <p className="hero__subtitle">
                A integração com a RNDS é realizada por meio de software.
                Algumas ferramentas para auxiliar esta integração são aqui
                documentadas.
              </p>
              <div>
                <Link
                  className={clsx(
                    "button button--outline button--secondary button--lg",
                    styles.getStarted
                  )}
                  to={useBaseUrl("docs/rel/objetivo-rel")}
                >
                  Acesso aos modelos clínicos: informacional e computacional
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
                A <b>integração com a RNDS</b> exige a produção de código que se
                beneficia de ferramentas de apoio ao desenvolvedor. Desde
                aquelas dirigidas para a produção de código até aquelas que
                apoio, ou oferecem suporte.
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

      <footer class="footer rodape">
        <div class="container container--fluid">
          <div class="row footer__links">
            <div class="col footer__col footer__center">
              <img
                class="footer__logo"
                alt="CGIS Logo"
                src={cgisImg}
                width="170"
                height="60"
              />
            </div>
            <div class="col footer__col footer__center">
              <img class="footer__logo" alt="DATASUS Logo" src={datasusImg} />
            </div>
            <div class="col footer__col">
              <img
                class="footer__logo"
                alt="Ministério da Saúde Logo"
                src={msImg}
              />
            </div>
          </div>
        </div>
      </footer>
    </Layout>
  );
}

export default Home;
