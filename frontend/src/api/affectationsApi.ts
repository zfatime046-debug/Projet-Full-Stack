import api from "./axiosConfig";

export const getAffectations = () => api.get("/affectations");
export const getAffectationsByPhase = (phaseId: number) => api.get(`/affectations/phase/${phaseId}`);
export const getAffectationsByEmploye = (employeId: number) => api.get(`/affectations/employe/${employeId}`);
export const createAffectation = (data: any) => api.post("/affectations", data);
export const updateAffectation = (data: any) => api.put("/affectations", data);
export const deleteAffectation = (employeId: number, phaseId: number) => api.delete(`/affectations/${employeId}/${phaseId}`);