# Portal de Integração com a RNDS

O portal é desenvolvido com [Docusaurus 2](https://docusaurus.io/) que se
apresenta como "um gerador de site estático moderno". O framework
empregado para definição de estilos é o [Infima](https://docusaurus.io/docs/styling-layout#styling-your-site-with-infima).

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

Disponibiliza a documentação no endereço https://kyriosdata.github.io/rnds.
