import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

const Factures = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [statutFiltre, setStatutFiltre] = useState("tous");

  const fetchData = async () => {
    try {
      const res = await api.get("/factures");
      setData(res.data);
    } catch (err) {
      toast.error("Erreur chargement");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const filtered = data.filter((item: any) => {
    const matchSearch =
      item.code?.toLowerCase().includes(search.toLowerCase()) ||
      item.statut?.toLowerCase().includes(search.toLowerCase());
    const matchStatut = statutFiltre === "tous" || item.statut === statutFiltre;
    return matchSearch && matchStatut;
  });

  const updateStatut = async (id: number, nouveauStatut: string) => {
    try {
      await api.put(`/factures/${id}/statut`, {
  statut: "PAYEE"
});
      toast.success(`Facture ${nouveauStatut}`);
      fetchData();
    } catch (err) {
      toast.error("Erreur mise à jour");
    }
  };

  const deleteFacture = async (id: number) => {
    if (window.confirm("Supprimer cette facture ?")) {
      await api.delete(`/factures/${id}`);
      toast.success("Facture supprimée");
      fetchData();
    }
  };

  const getMontant = (item: any) => {
    const val = item.montant ?? item.montantTtc ?? item.total ?? 0;
    const num = parseFloat(val);
    return isNaN(num) ? "0 €" : `${num.toLocaleString()} €`;
  };

  if (loading) return <div>Chargement...</div>;

  const columns = data[0] ? Object.keys(data[0]) : [];

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold">Factures</h1>

      <input
        type="text"
        placeholder="🔍 Rechercher par code ou statut"
        className="w-full border p-2 rounded"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      <div className="flex items-center gap-4 bg-gray-50 p-3 rounded-lg">
        <span className="text-sm font-medium">Statut :</span>
        <select
          className="border rounded p-2"
          value={statutFiltre}
          onChange={(e) => setStatutFiltre(e.target.value)}
        >
          <option value="tous">Toutes</option>
          <option value="EMISE">Émise</option>
          <option value="PAYEE">Payée</option>
        </select>
      </div>

      <div className="bg-white rounded-xl shadow overflow-auto">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              {columns.map((col) => (
                <th key={col} className="px-6 py-3 text-left">{col}</th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((item: any) => (
              <tr key={item.id} className="border-t">
                {columns.map((col) => (
                  <td key={col} className="px-6 py-4">
                    {col === "montant" || col === "montantTtc" || col === "total"
                      ? getMontant(item)
                      : item[col]}
                  </td>
                ))}
                <td className="px-6 py-4 space-x-2">
                  {item.statut !== "PAYEE" && (
                    <button
                      onClick={() => updateStatut(item.id, "PAYEE")}
                      className="bg-green-600 text-white px-3 py-1 rounded text-sm"
                    >
                      Marquer payée
                    </button>
                  )}
                  <button
                    onClick={() => deleteFacture(item.id)}
                    className="text-red-600"
                  >
                    Supprimer
                  </button>
                </td>
              </tr>
            ))}
            {filtered.length === 0 && (
              <tr><td colSpan={columns.length + 1} className="text-center py-4">Aucune facture trouvée</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Factures;