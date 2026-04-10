import React from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { LogOut } from "lucide-react";

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <header className="bg-white shadow-sm px-6 py-3 flex justify-between items-center border-b">
      <h1 className="text-xl font-semibold text-gray-800">ProjetManager</h1>
      <div className="flex items-center gap-4">
        {user && (
          <span className="text-sm text-gray-600">
            👋 {user.login || user.email}
          </span>
        )}
        <button
          onClick={handleLogout}
          className="flex items-center gap-2 bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition"
        >
          <LogOut size={18} />
          Déconnexion
        </button>
      </div>
    </header>
  );
};

export default Navbar;