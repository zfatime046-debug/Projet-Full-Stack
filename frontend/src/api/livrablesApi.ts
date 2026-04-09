import api from "./axiosConfig";

export const getLivrablesByPhase = (phaseId: number) => api.get(`/phases/${phaseId}/livrables`);
export const createLivrable = (phaseId: number, data: any) => api.post(`/phases/${phaseId}/livrables`, data);
export const updateLivrable = (id: number, data: any) => api.put(`/livrables/${id}`, data);
export const deleteLivrable = (id: number) => api.delete(`/livrables/${id}`);