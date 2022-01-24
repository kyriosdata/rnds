import RNDS from "./rnds.js";

process.on('uncaughtException', function(err) {
    console.log("\nA exceção abaixo ocorreu e não foi tratada...\n");
    console.log(err);
})

const rnds = await RNDS.cliente(true, true, true);

rnds.checkVersion()
    .then(c => console.log("FHIR VERSION", c ? "ok" : "erro"))
    .catch(() => console.log("erro ao verificar versão..."));

rnds.cnes("2337991")
    .then(cnes  => console.log("CNES", cnes.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNES"));

rnds.cns("980016287385192")
    .then(cns  => console.log("CNS", cns.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNS"));

// Comentado para evitar divulgação de um CPF
// rnds.cpf("<cpf aqui>")
//     .then(cpf  => console.log("CPF", cpf.code === 200 ? "ok" : "erro"))
//     .catch(() => console.log("erro ao obter CNES"));

rnds.cnpj("01567601000143")
    .then(cnpj  => console.log("CNPJ", cnpj.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNPJ"));