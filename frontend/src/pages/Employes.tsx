import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

const Employes = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [search, setSearch] = useState("");
  const [form, setForm] = useState({
    matricule: "",
    nom: "",
    prenom: "",
    email: "",
    login: "",
    telephone: "",
  });

  const fetchData = async () => {
    try {
      const res = await api.get("/employes");
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

  const filtered = data.filter((item: any) =>
    `${item.nom} ${item.prenom} ${item.email} ${item.matricule}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editing) {
        await api.put(`/employes/${editing.id}`, form);
        toast.success("Employé modifié");
      } else {
        await api.post("/employes", form);
        toast.success("Employé ajouté");
      }
      setShowModal(false);
      setEditing(null);
      setForm({ matricule: "", nom: "", prenom: "", email: "", login: "", telephone: "" });
      fetchData();
    } catch (err) {
      toast.error("Erreur");
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Supprimer cet employé ?")) {
      await api.delete(`/employes/${id}`);
      toast.success("Supprimé");
      fetchData();
    }
  };

  const openEdit = (item: any) => {
    setEditing(item);
    setForm({
      matricule: item.matricule,
      nom: item.nom,
      prenom: item.prenom,
      email: item.email,
      login: item.login,
      telephone: item.telephone || "",
    });
    setShowModal(true);
  };

  if (loading) return <div>Chargement...</div>;

  const columns = data[0] ? Object.keys(data[0]) : [];

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Employés</h1>
        <button
          onClick={() => {
            setEditing(null);
            setForm({ matricule: "", nom: "", prenom: "", email: "", login: "", telephone: "" });
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
        placeholder="🔍 Rechercher (nom, prénom, email, matricule)"
        className="w-full border p-2 rounded"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

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
                  <td key={col} className="px-6 py-4">{item[col]}</td>
                ))}
                <td className="px-6 py-4 space-x-2">
                  <button onClick={() => openEdit(item)} className="text-blue-600">Modifier</button>
                  <button onClick={() => handleDelete(item.id)} className="text-red-600">Supprimer</button>
                </td>
              </tr>
            ))}
            {filtered.length === 0 && (
              <tr><td colSpan={columns.length + 1} className="text-center py-4">Aucun résultat</td></tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-xl w-96">
            <h2 className="text-xl font-bold mb-4">{editing ? "Modifier" : "Ajouter"} un employé</h2>
            <form onSubmit={handleSubmit} className="space-y-3">
              <input className="w-full border p-2 rounded" placeholder="Matricule" value={form.matricule} onChange={e => setForm({ ...form, matricule: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Nom" value={form.nom} onChange={e => setForm({ ...form, nom: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Prénom" value={form.prenom} onChange={e => setForm({ ...form, prenom: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Login" value={form.login} onChange={e => setForm({ ...form, login: e.target.value })} required />
              <input className="w-full border p-2 rounded" placeholder="Téléphone" value={form.telephone} onChange={e => setForm({ ...form, telephone: e.target.value })} />
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

export default Employes;