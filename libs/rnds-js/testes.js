import RNDS from "./rnds.js";

const rnds = await RNDS.cliente(true, true, true);
rnds.verifica().then(console.log);
