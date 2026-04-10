import React from "react";
import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Building2,
  Users,
  FolderKanban,
  CheckSquare,
  DollarSign,
  UserCheck,
  FileText,
  Package,
} from "lucide-react";

const Sidebar = () => {
  const navItems = [
    { name: "Dashboard", path: "/", icon: LayoutDashboard },
    { name: "Organismes", path: "/organismes", icon: Building2 },
    { name: "Employés", path: "/employes", icon: Users },
    { name: "Projets", path: "/projets", icon: FolderKanban },
    { name: "Phases", path: "/phases", icon: CheckSquare },
    { name: "Factures", path: "/factures", icon: DollarSign },
    { name: "Affectations", path: "/affectations", icon: UserCheck },
    { name: "Documents", path: "/documents", icon: FileText },
    { name: "Livrables", path: "/livrables", icon: Package },
  ];

  return (
    <aside className="w-64 bg-gray-900 text-white flex flex-col h-screen">
      <div className="p-4 text-xl font-bold border-b border-gray-700">
        ProjetManager
      </div>
      <nav className="flex-1 p-4 space-y-1">
        {navItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              `flex items-center gap-3 px-3 py-2 rounded-lg transition ${
                isActive
                  ? "bg-indigo-600 text-white"
                  : "text-gray-300 hover:bg-gray-800"
              }`
            }
          >
            <item.icon className="w-5 h-5" />
            {item.name}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;