import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

const Phases = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [search, setSearch] = useState("");
  const [filtreRealisation, setFiltreRealisation] = useState("tous");
  const [filtreFacturation, setFiltreFacturation] = useState("tous");
  const [filtrePaiement, setFiltrePaiement] = useState("tous");
  const [form, setForm] = useState({
    code: "",
    libelle: "",
    description: "",
    dateDebut: "",
    dateFin: "",
    montant: "",
  });

  const fetchData = async () => {
    try {
      const res = await api.get("/phases");
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

  const filteredBySearch = data.filter((item: any) =>
    `${item.code} ${item.libelle} ${item.description || ""}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const filtered = filteredBySearch.filter((item: any) => {
    if (filtreRealisation !== "tous" && item.etatRealisation !== (filtreRealisation === "oui")) return false;
    if (filtreFacturation !== "tous" && item.etatFacturation !== (filtreFacturation === "oui")) return false;
    if (filtrePaiement !== "tous" && item.etatPaiement !== (filtrePaiement === "oui")) return false;
    return true;
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editing) {
        await api.put(`/phases/${editing.id}`, form);
        toast.success("Phase modifiée");
      } else {
        await api.post("/phases", form);
        toast.success("Phase ajoutée");
      }
      setShowModal(false);
      setEditing(null);
      setForm({ code: "", libelle: "", description: "", dateDebut: "", dateFin: "", montant: "" });
      fetchData();
    } catch (err) {
      toast.error("Erreur");
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Supprimer cette phase ?")) {
      await api.delete(`/phases/${id}`);
      toast.success("Supprimée");
      fetchData();
    }
  };

  const openEdit = (item: any) => {
    setEditing(item);
    setForm({
      code: item.code,
      libelle: item.libelle,
      description: item.description || "",
      dateDebut: item.dateDebut?.split("T")[0] || "",
      dateFin: item.dateFin?.split("T")[0] || "",
      montant: item.montant,
    });
    setShowModal(true);
  };

  if (loading) return <div>Chargement...</div>;

  const columns = data[0] ? Object.keys(data[0]) : [];

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Phases</h1>
        <button
          onClick={() => {
            setEditing(null);
            setForm({ code: "", libelle: "", description: "", dateDebut: "", dateFin: "", montant: "" });
            setShowModal(true);
          }}
          className="bg-indigo-600 text-white px-4 py-2 rounded"
        >
          + Ajouter
        </button>
      </div>

      {/* 🔍 Barre de recherche */}
      <input
        type="text"
        placeholder="🔍 Rechercher par code, libellé ou description"
        className="w-full border p-2 rounded"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {/* 🎯 Filtres par statut */}
      <div className="flex flex-wrap gap-4 bg-gray-50 p-3 rounded-lg">
        <div className="flex items-center gap-2">
          <span className="text-sm font-medium">Réalisation :</span>
          <select className="border rounded p-1 text-sm" value={filtreRealisation} onChange={e => setFiltreRealisation(e.target.value)}>
            <option value="tous">Tous</option>
            <option value="oui">Réalisée</option>
            <option value="non">Non réalisée</option>
          </select>
        </div>
        <div className="flex items-center gap-2">
          <span className="text-sm font-medium">Facturation :</span>
          <select className="border rounded p-1 text-sm" value={filtreFacturation} onChange={e => setFiltreFacturation(e.target.value)}>
            <option value="tous">Tous</option>
            <option value="oui">Facturée</option>
            <option value="non">Non facturée</option>
          </select>
        </div>
        <div className="flex items-center gap-2">
          <span className="text-sm font-medium">Paiement :</span>
          <select className="border rounded p-1 text-sm" value={filtrePaiement} onChange={e => setFiltrePaiement(e.target.value)}>
            <option value="tous">Tous</option>
            <option value="oui">Payée</option>
            <option value="non">Non payée</option>
          </select>
        </div>
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
                    {typeof item[col] === "boolean" ? (item[col] ? "✅" : "❌") : item[col]}
                  </td>
                ))}
                <td className="px-6 py-4 space-x-2">
                  <button onClick={() => openEdit(item)} className="text-blue-600">Modifier</button>
                  <button onClick={() => handleDelete(item.id)} className="text-red-600">Supprimer</button>
                </td>
              </tr>
            ))}
            {filtered.length === 0 && (
              <tr><td colSpan={columns.length + 1} className="text-center py-4">Aucune phase trouvée</td></tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-xl w-96">
            <h2 className="text-xl font-bold mb-4">{editing ? "Modifier" : "Ajouter"} une phase</h2>
            <form onSubmit={handleSubmit} className="space-y-3">
              <input className="w-full border p-2 rounded" placeholder="Code" value={form.code} onChange={e => setForm({ ...form, code: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Libellé" value={form.libelle} onChange={e => setForm({ ...form, libelle: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Description" value={form.description} onChange={e => setForm({ ...form, description: e.target.value })} />
              <input type="date" className="w-full border p-2 rounded" value={form.dateDebut} onChange={e => setForm({ ...form, dateDebut: e.target.value })} required />
              <input type="date" className="w-full border p-2 rounded" value={form.dateFin} onChange={e => setForm({ ...form, dateFin: e.target.value })} required />
              <input type="number" className="w-full border p-2 rounded" placeholder="Montant" value={form.montant} onChange={e => setForm({ ...form, montant: e.target.value })} required />
              <div className="flex justify-end gap-2">
                <button type="button" onClick={() => setShowModal(false)} className="px-4 py-2 border rounded">Annuler</button>
                <button type="submit" className="px-4 py-2 bg-indigo-600 text-white rounded">Enregistrer</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Phases;