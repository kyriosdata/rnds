import RNDS from "./rnds.js";

process.on('uncaughtException', (err) => console.log("!!??\n", err));

async function status(requisicao, msg) {
    const retorno = await requisicao;
    console.log(retorno.code === 200 ? "ok" : "erro", msg);
}

// Informações públicas empregadas nos testes
const CNPJ = "01567601000143";
const CNES = "2337991";
const CNS = "980016287385192";

try {
    const rnds = await RNDS.cliente(true, true, true);

    // await status(rnds.capability(), "CapabilityStatement");
    //
    // console.log(await rnds.checkVersion() ? "ok" : "erro", "FHIR VERSION");
    // console.log(await rnds.tokenDisponivel() ? "ok" : "erro", "Token disponível")
    //
    // await status(rnds.atendimento(CNES, CNS, CNS), "contextoAtendimento");
    // await status(rnds.cnes(CNES), "CNES");
    //
    // // Obtém CPF para uso posterior (informação sensível não registrada)
    // const resposta = await rnds.cns(CNS)
    // console.log(resposta.code === 200 ? "ok" : "erro", "CNS");
    // const idt = JSON.parse(resposta.retorno).identifier;
    // const idx = idt.findIndex(i => i.system.endsWith("/cpf"));
    // const codigoCPF = idt[idx].value;
    //
    // await status(rnds.cpf(codigoCPF), "CPF");
    // await status(rnds.cnpj(CNPJ), "CNPJ");
    // await status(rnds.lotacoes(CNS, CNES), "CNS/CNES");
    // await status(rnds.lotacaoPorCns(CNS), "CNS (lotações)");
    // await status(rnds.lotacaoPorCnes(CNES), "CNES (lotações)");
    // await status(rnds.lotacaoCnsEmCnes(CNS, CNES), "CNS/CNES");
    // await status(rnds.pacientePorCns(CNS), "CNS (Bundle)");
    // await status(rnds.pacientePorCpf(codigoCPF), "CPF (Patient)");
    // await status(rnds.paciente(CNS), "CNS (paciente - Patient)");
    //
    // await status(rnds.cnsDoPaciente(codigoCPF), "CNS a partir do CPF");
    //
    // const buscaPorNome = rnds.pacientePorNome("termo1 termo2", "maria tereza");
    // await status(buscaPorNome, "busca por nome e nome da mãe");
    //
    // const buscaPorData = rnds.pacientePorNascimento("t1 t2", "1967-08-06");
    // await status(buscaPorData, "busca por nome e data de nascimento");
    //
    // const buscaPorLocal = rnds.pacientePorLocalDeNascimento("t1 t2", "520870");
    // await status(buscaPorLocal, "busca por nome e local de nascimento");

    await status(rnds.loinc("94306-8"), "LOINC");
    await status(rnds.amostraGal("CORIZA"), "Amostra Biológica GAL");

    await status(rnds.exameGal("CVIDGG"), "Exames GAL");

} catch (erro) {
    console.log(erro);
}