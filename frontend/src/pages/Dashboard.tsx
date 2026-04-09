import React, { useEffect, useState } from "react";
import { getTableauDeBord } from "../api/reportingApi";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from "recharts";
import { FolderKanban, CheckCircle, Clock, AlertCircle, DollarSign, TrendingUp, Users, Briefcase } from "lucide-react";

const Dashboard = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await getTableauDeBord();
        console.log("Stats reçues:", response.data);
        setStats(response.data);
      } catch (err) {
        console.error("Erreur chargement dashboard:", err);
        setError("Impossible de charger les statistiques");
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  if (loading) return <div className="p-6 text-center">Chargement du tableau de bord...</div>;
  if (error) return <div className="p-6 text-center text-red-600">{error}</div>;
  if (!stats) return <div className="p-6 text-center">Aucune donnée disponible</div>;

  // Données pour le graphique en barres
  const barData = [
    { name: "Projets en cours", valeur: stats.nombreProjetsEnCours || 0 },
    { name: "Projets clôturés", valeur: stats.nombreProjetsClotures || 0 },
  ];

  // Données pour le graphique circulaire (phases)
  const pieData = [
    { name: "Terminées non facturées", valeur: stats.nombrePhasesTermineesNonFacturees || 0, color: "#f59e0b" },
    { name: "Facturées non payées", valeur: stats.nombrePhasesFactureesNonPayees || 0, color: "#ef4444" },
    { name: "Payées", valeur: stats.nombrePhasesPayees || 0, color: "#10b981" },
  ];

  const StatCard = ({ title, value, icon: Icon, colorClass, subtitle }) => (
    <div className="bg-white rounded-xl shadow-sm p-6 dark:bg-gray-800 border border-gray-100 dark:border-gray-700 hover:shadow-md transition-shadow">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm text-gray-500 dark:text-gray-400 font-medium">{title}</p>
          <p className="text-3xl font-bold text-gray-900 dark:text-white mt-1">{value}</p>
          {subtitle && <p className="text-xs text-gray-400 mt-1">{subtitle}</p>}
        </div>
        <div className={`p-3 rounded-full ${colorClass}`}>
          <Icon className="w-6 h-6 text-white" />
        </div>
      </div>
    </div>
  );

  return (
    <div className="space-y-6">
      {/* En-tête */}
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Tableau de bord</h1>
          <p className="text-gray-500 dark:text-gray-400 mt-1">Bienvenue, voici l'état actuel de vos projets</p>
        </div>
      </div>

      {/* Cartes KPI - Première ligne */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard 
          title="Projets en cours" 
          value={stats.nombreProjetsEnCours || 0} 
          icon={FolderKanban} 
          colorClass="bg-blue-500"
          subtitle="En cours d'exécution"
        />
        <StatCard 
          title="Projets clôturés" 
          value={stats.nombreProjetsClotures || 0} 
          icon={CheckCircle} 
          colorClass="bg-green-500"
          subtitle="Terminés"
        />
        <StatCard 
          title="Phases payées" 
          value={stats.nombrePhasesPayees || 0} 
          icon={DollarSign} 
          colorClass="bg-emerald-500"
          subtitle="Factures réglées"
        />
        <StatCard 
          title="Taux d'avancement" 
          value={stats.nombreProjetsEnCours + stats.nombreProjetsClotures > 0 
            ? Math.round((stats.nombreProjetsClotures / (stats.nombreProjetsEnCours + stats.nombreProjetsClotures)) * 100) 
            : 0} 
          icon={TrendingUp} 
          colorClass="bg-purple-500"
          subtitle="% projets terminés"
        />
      </div>

      {/* Deuxième ligne : État financier des phases */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <StatCard 
          title="Phases terminées (Non facturées)" 
          value={stats.nombrePhasesTermineesNonFacturees || 0} 
          icon={AlertCircle} 
          colorClass="bg-yellow-500"
          subtitle="À facturer"
        />
        <StatCard 
          title="Phases facturées (Non payées)" 
          value={stats.nombrePhasesFactureesNonPayees || 0} 
          icon={Clock} 
          colorClass="bg-orange-500"
          subtitle="En attente de paiement"
        />
        <StatCard 
          title="Phases payées" 
          value={stats.nombrePhasesPayees || 0} 
          icon={CheckCircle} 
          colorClass="bg-green-500"
          subtitle="Total encaissé"
        />
      </div>

      {/* Graphiques */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-4">
        {/* Graphique en barres */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 dark:bg-gray-800 dark:border-gray-700">
          <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">Répartition des projets</h2>
          <div className="h-64 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={barData}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis dataKey="name" />
                <YAxis allowDecimals={false} />
                <Tooltip cursor={{ fill: "transparent" }} />
                <Bar dataKey="valeur" fill="#6366f1" radius={[8, 8, 0, 0]} barSize={60} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Graphique circulaire */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 dark:bg-gray-800 dark:border-gray-700">
          <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">État financier des phases</h2>
          <div className="h-64 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={pieData}
                  cx="50%"
                  cy="50%"
                  innerRadius={60}
                  outerRadius={80}
                  paddingAngle={5}
                  dataKey="valeur"
                  label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                >
                  {pieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;