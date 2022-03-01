import RNDS from "./rnds.js";

process.on('uncaughtException', (err) => console.log("!!??\n", err));

async function status(requisicao, msg) {
    const retorno = await requisicao;
    console.log(retorno.code === 200 ? "ok" : "\n\n\t**** ERRO ***\n", msg);
}

// Informações públicas empregadas nos testes
const CNPJ = "01567601000143";
const CNES = "2337991";
const CNS = "980016287385192";

try {
    const rnds = await RNDS.cliente(false, true, true);

    await status(rnds.capability(), "CapabilityStatement");

    console.log(await rnds.checkVersion() ? "ok" : "erro", "FHIR VERSION");
    console.log(await rnds.tokenDisponivel() ? "ok" : "erro", "Token disponível")

    await status(rnds.atendimento(CNES, CNS, CNS), "contextoAtendimento");
    await status(rnds.cnes(CNES), "CNES");

    // Obtém CPF para uso posterior (informação sensível não registrada)
    const resposta = await rnds.cns(CNS)
    console.log(resposta.code === 200 ? "ok" : "erro", "CNS");
    const idt = JSON.parse(resposta.retorno).identifier;
    const idx = idt.findIndex(i => i.system.endsWith("/cpf"));
    const codigoCPF = idt[idx].value;

    await status(rnds.cpf(codigoCPF), "CPF");
    await status(rnds.cnpj(CNPJ), "CNPJ");
    await status(rnds.lotacoes(CNS, CNES), "CNS/CNES");
    await status(rnds.lotacaoPorCns(CNS), "CNS (lotações)");
    await status(rnds.lotacaoPorCnes(CNES), "CNES (lotações)");
    await status(rnds.lotacaoCnsEmCnes(CNS, CNES), "CNS/CNES");
    await status(rnds.pacientePorCns(CNS), "CNS (Bundle)");
    await status(rnds.pacientePorCpf(codigoCPF), "CPF (Patient)");
    await status(rnds.paciente(CNS), "CNS (paciente - Patient)");
    await status(rnds.cnsDoPaciente(codigoCPF), "CNS a partir do CPF");

    const buscaPorNome = rnds.pacientePorNome("termo1 termo2", "maria tereza");
    await status(buscaPorNome, "busca por nome e nome da mãe");

    const buscaPorData = rnds.pacientePorNascimento("t1 t2", "1967-08-06");
    await status(buscaPorData, "busca por nome e data de nascimento");

    const buscaPorLocal = rnds.pacientePorLocalDeNascimento("t1 t2", "520870");
    await status(buscaPorLocal, "busca por nome e local de nascimento");

    // CodeSystem

    await status(rnds.caraterAtendimento("01"), "caráter de atendimento");
    await status(rnds.cbhpm("001"), "CBHPM");
    await status(rnds.cbo("223505"), "CBO");
    await status(rnds.ciap2("A01"), "CIAP-2");
    await status(rnds.cid10("A90"), "CID-10");
    await status(rnds.dose("9"), "dose");
    await status(rnds.estrategiaVacinacao("1"), "estratégia de vacinação");
    await status(rnds.financiamento("01"), "financiamento");
    await status(rnds.grupoAtendimento("001001"), "grupo atendimento");
    await status(rnds.imunobiologico("99"), "imunobiológico");
    await status(rnds.localAplicacao("0"), "local de aplicação");
    await status(rnds.modalidadeAssistencial("01"), "modalidade assistencial");
    await status(rnds.motivoDesfecho("01"), "motivo do desfecho");
    await status(rnds.naturezaJuridica("1"), "natureza jurídica");
    await status(rnds.nomeExameGal("CVIDGG"), "Exames GAL");
    await status(rnds.nomeExameLoinc("94306-8"), "LOINC");
    await status(rnds.pais("10"), "país");
    await status(rnds.procedencia("12"), "procedência");
    await status(rnds.raca("02"), "raça");
    await status(rnds.resultadoQualitativo("3"), "resultado qualitativo");
    await status(rnds.situacaoCondicaoIndividuo("31"), "situação / condição do indivíduo");
    await status(rnds.subgrupoTabelaSUS("0204"), "subgrupo tabela SUS");
    await status(rnds.tabelaSUS("0101030029"), "tabela SUS");
    await status(rnds.tipoAmostra("CORIZA"), "Amostra Biológica GAL");
    await status(rnds.tipoDocumento("SA"), "tipo de documento");
    await status(rnds.tipoEstabelecimento("68"), "tipo de estabelecimento");
    await status(rnds.viaAdministracao("3"), "via de administração");

} catch (erro) {
    console.log(erro);
}