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
        <i>transformação digital</i> da saúde no Brasil.
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
      class="meuleiaute"
      title={`${siteConfig.title}`}
      description="Guia de Integração da RNDS"
    >
      <header className={clsx("hero hero--primary", styles.heroBanner)}>
        <div className="container">
          <div class="row">
            <div class="col">
              <h1 className="hero__title titulo">
                Rede Nacional de Dados em Saúde (RNDS)
              </h1>
              <p className="hero__subtitle">
                Este portal tem como objetivo orientar gestores de
                estabelecimentos de Saúde e profissionais de tecnologia da
                informação, acerca da integração com a RNDS, visando a
                interoperabilidade de informações em saúde para continuidade do
                cuidado do usuário, no momento da assistência, em qualquer lugar
                do Brasil.
              </p>
              <div>
                <Link
                  className={clsx(
                    "button button--outline button--secondary button--lg",
                    styles.getStarted
                  )}
                  to={useBaseUrl("docs/passo-a-passo")}
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
                A <b>integração com a RNDS</b> é apresentada na forma de uma
                sequência de atividades (passo a passo), que envolve o gestor
                (responsável pelo estabelecimento de saúde) e o integrador
                (desenvolvedor de software). Esta integração promove a troca de
                informação em saúde, descrita pelos modelos Informacional e
                Computacional, acompanhados de outras orientações. Um curso
                completo, gratuito e online encontra-se disponível&nbsp;
                <a href="https://www.unasus.gov.br/cursos/oferta/418988">
                  aqui
                </a>
                .
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
            <div class="col footer__col">
              <img
                class="footer__logo"
                alt="Facebook Open Source Logo"
                src="img/acesso.png"
              />
            </div>
            <div class="col footer__col">
              <img
                class="footer__logo"
                alt="Facebook Open Source Logo"
                src="img/datasus.png"
              />
            </div>
            <div class="col footer__col">
              <img
                class="footer__logo"
                alt="Facebook Open Source Logo"
                src="img/ms.png"
              />
            </div>
          </div>
        </div>
      </footer>
    </Layout>
  );
}

export default Home;
