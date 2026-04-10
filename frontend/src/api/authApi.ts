import api from "./axiosConfig";

export const login = (data: { login: string; password: string }) =>
  api.post("/auth/login", data);

export const getMe = () => api.get("/auth/me");
