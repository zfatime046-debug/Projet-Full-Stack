import React, { useEffect, useState } from "react";
import { getTableauDeBord } from "../api/reportingApi";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from "recharts";
import { FolderKanban, CheckCircle, AlertCircle, DollarSign, TrendingUp } from "lucide-react";

const Dashboard = () => {
  const [stats, setStats] = useState<any>(null);

  useEffect(() => {
    getTableauDeBord().then(res => setStats(res.data)).catch(err => console.error(err));
  }, []);

  if (!stats) return <div className="p-6">Chargement...</div>;

  const barData = [
    { name: "Projets en cours", valeur: stats.nombreProjetsEnCours || 0 },
    { name: "Projets clôturés", valeur: stats.nombreProjetsClotures || 0 },
  ];

  const pieData = [
    { name: "Terminées non facturées", valeur: stats.nombrePhasesTermineesNonFacturees || 0, color: "#f59e0b" },
    { name: "Facturées non payées", valeur: stats.nombrePhasesFactureesNonPayees || 0, color: "#ef4444" },
    { name: "Payées", valeur: stats.nombrePhasesPayees || 0, color: "#10b981" },
  ];

  const StatCard = ({ title, value, icon: Icon, color }) => (
    <div className="bg-white p-5 rounded-xl shadow border flex items-center justify-between">
      <div><p className="text-gray-500">{title}</p><p className="text-3xl font-bold">{value}</p></div>
      <div className={`p-3 rounded-full ${color}`}><Icon className="text-white" /></div>
    </div>
  );

  return (
    <div className="space-y-8 p-6">
      <h1 className="text-2xl font-bold">Tableau de bord</h1>
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <StatCard title="Projets en cours" value={stats.nombreProjetsEnCours} icon={FolderKanban} color="bg-blue-500" />
        <StatCard title="Projets clôturés" value={stats.nombreProjetsClotures} icon={CheckCircle} color="bg-green-500" />
        <StatCard title="Phases payées" value={stats.nombrePhasesPayees} icon={DollarSign} color="bg-emerald-500" />
        <StatCard title="Taux complétion" value={Math.round((stats.nombreProjetsClotures / (stats.nombreProjetsEnCours + stats.nombreProjetsClotures)) * 100) || 0} icon={TrendingUp} color="bg-purple-500" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-4 rounded-xl shadow"><h2 className="font-semibold mb-4">Répartition projets</h2><ResponsiveContainer width="100%" height={250}><BarChart data={barData}><CartesianGrid /><XAxis dataKey="name" /><YAxis /><Tooltip /><Bar dataKey="valeur" fill="#6366f1" /></BarChart></ResponsiveContainer></div>
        <div className="bg-white p-4 rounded-xl shadow"><h2 className="font-semibold mb-4">État financier phases</h2><ResponsiveContainer width="100%" height={250}><PieChart><Pie data={pieData} dataKey="valeur" nameKey="name" cx="50%" cy="50%" innerRadius={60} outerRadius={80} label>{pieData.map((e, i) => <Cell key={i} fill={e.color} />)}</Pie><Tooltip /></PieChart></ResponsiveContainer></div>
      </div>
    </div>
  );
};

export default Dashboard;