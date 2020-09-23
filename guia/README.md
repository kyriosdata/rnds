# Guia de Integração RNDS

O Guia faz uso da ferramenta [Docusaurus 2](https://v2.docusaurus.io/).
Conforme a própria definição no portal, "um gerador de site estático moderno".

### Instalação

```
$ yarn
```

### Disponibilizar localmente (desenvolvimento)

```
$ yarn start
```

Esse comando disponibiliza o site estático no endereço
`localhost:3000/rnds`

### Build

```
$ yarn build
```

Este comando produz o conteúdo estático no diretório `build`. O conteúdo
deste diretório pode ser disponibilizado em qualquer serviço de hospedagem
de conteúdo estático.

### Liberação (gh-pages branch)

```
$ GIT_USER=<Your GitHub username> yarn deploy
```

Disponibiliza a documentação no endereço
`https://kyriosdata.github.io/rnds`.
