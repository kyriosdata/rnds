import RNDS from "./rnds.js";

process.on('uncaughtException', function (err) {
    console.log("\nA exceção abaixo ocorreu e não foi tratada...\n");
    console.log(err);
})

const rnds = await RNDS.cliente(true, true, true);

rnds.capability()
    .then(c => console.log("CapabilityStatement: ", c.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao recuperar CapabilityStatement..."));

rnds.checkVersion()
    .then(c => console.log("FHIR VERSION", c ? "ok" : "erro"))
    .catch(() => console.log("erro ao verificar versão..."));

rnds.contextoAtendimento("2337991", "980016287385192", "980016287385192")
    .then(ca => console.log("contextoAtendimento", ca.code === 200 ? "ok" : "erro"))
    .catch(console.log);

rnds.cnes("2337991")
    .then(cnes => console.log("CNES", cnes.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNES"));

// Usado para guardar CPF para consulta posterior.
// Desta forma, nenhuma informação "sensível" é
// fornecida neste arquivo.

let codigoCPF;

await rnds.cns("980016287385192")
    .then(cns => {
        console.log("CNS", cns.code === 200 ? "ok" : "erro");
        const idt = JSON.parse(cns.retorno).identifier;
        const idx = idt.findIndex(i => i.system.endsWith("/cpf"));
        codigoCPF = idt[idx].value;
    })
    .catch(() => console.log("erro ao obter CNS"));

rnds.cpf(codigoCPF)
    .then(cpf => console.log("CPF", cpf.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CPF"));

rnds.cnpj("01567601000143")
    .then(cnpj => console.log("CNPJ", cnpj.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter CNPJ"));

rnds.lotacoes("980016287385192", "2337991")
    .then(l => console.log("CNS/CNES (lotações)", l.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter lotações"));

rnds.lotacaoPorCns("980016287385192")
    .then(l => console.log("CNS (lotações)", l.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter lotações"));

rnds.lotacaoPorCnes("2337991")
    .then(l => console.log("CNES (lotações)", l.code === 200 ? "ok" : "erro"))
    .catch(() => console.log("erro ao obter lotações"));