import RNDS from "./rnds.js";

process.on('uncaughtException', function(err) {
    console.log();
    console.log("A exceção abaixo ocorreu e não foi tratada...");
    console.log();
    console.log(err);
})

// logging está habilitado (primeiro argumento)
const rnds = await RNDS.cliente(true, true, true);

// rnds.verifica().then(resposta => {
//     if (resposta) {
//         console.log("Acesso verificado.");
//     } else {
//         console.log("Falha na verificação.");
//     }
// });

// Estilo
// rnds.checkVersion().then(console.log).catch(() => console.log("problema..."));

// Alternativa
try {
    const compativel = await rnds.checkVersion();
    const msg = compativel ? "sim" : "não";
    console.log("Há compatibilidade:", msg);
} catch (error) {
    console.log("houve algum problema...");
}