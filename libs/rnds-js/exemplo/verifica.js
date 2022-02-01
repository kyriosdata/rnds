import RNDS from "rnds";

const objeto = await RNDS.cliente(true, true, true);

objeto.verifica().then(console.log);
