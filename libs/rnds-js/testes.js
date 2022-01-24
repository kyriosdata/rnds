import RNDS from "./rnds.js";

// logging está habilitado (primeiro argumento)
const rnds = await RNDS.cliente(true, true, true);

rnds.verifica().then(resposta => {
    if (resposta) {
        console.log("Acesso verificado.");
    } else {
        console.log("Falha na verificação.");
    }
});
