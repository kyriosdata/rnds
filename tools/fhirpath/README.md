## Prática com FHIRPath

### Instalação

```shell
$ git clone https://github.com/kyriosdata/rnds
$ cd rnds/tools/fhirpath
$ npm install
```

### Operação trivial

```shell
$ cd rnds/tools/fhirpath
$ npm install
$ npm run fhirpath -- -f exemplo-original.json -e "entry.count()"
```
