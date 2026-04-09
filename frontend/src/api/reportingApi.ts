import api from "./axiosConfig";

export const getTableauDeBord = () => api.get("/reporting/tableau-de-bord");
export const getProjetsEnCours = () => api.get("/reporting/projets/en-cours");
export const getProjetsClotures = () => api.get("/reporting/projets/clotures");
export const getPhasesTermineesNonFacturees = () => api.get("/reporting/phases/terminees-non-facturees");
export const getPhasesFactureesNonPayees = () => api.get("/reporting/phases/facturees-non-payees");
export const getPhasesPayees = () => api.get("/reporting/phases/payees");
