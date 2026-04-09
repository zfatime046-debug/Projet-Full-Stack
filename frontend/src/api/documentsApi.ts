import api from "./axiosConfig";

export const getDocumentsByProjet = (projetId: number) => api.get(`/projets/${projetId}/documents`);
export const createDocument = (projetId: number, data: FormData) => 
  api.post(`/projets/${projetId}/documents`, data, { headers: { "Content-Type": "multipart/form-data" } });
export const updateDocument = (id: number, data: any) => api.put(`/documents/${id}`, data);
export const deleteDocument = (id: number) => api.delete(`/documents/${id}`);
export const downloadDocument = (id: number) => api.get(`/documents/${id}/download`, { responseType: "blob" });
