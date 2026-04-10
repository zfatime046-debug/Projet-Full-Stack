import React, { createContext, useState, useEffect, useContext } from "react";
import { login as apiLogin, getMe } from "../api/authApi";
import type { User, LoginResponse } from "../types";

interface AuthContextType {
  user: User | null;
  login: (credentials: { login: string; password: string }) => Promise<void>;
  logout: () => void;
  hasRole: (role: string) => boolean;
  hasAnyRole: (roles: string[]) => boolean;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initAuth = async () => {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const response = await getMe();
          setUser(response.data);
        } catch (error) {
          localStorage.removeItem("token");
        }
      }
      setLoading(false);
    };
    initAuth();
  }, []);

  const login = async (credentials: { login: string; password: string }) => {
    const response = await apiLogin(credentials);
    const { token, login: userLogin, role } = response.data;
    localStorage.setItem("token", token);
    setUser({ id: 0, login: userLogin, email: "", role });
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  const hasRole = (role: string) => {
    return user?.role === role;
  };

  const hasAnyRole = (roles: string[]) => {
    return roles.some((role) => hasRole(role));
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, hasRole, hasAnyRole, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};
