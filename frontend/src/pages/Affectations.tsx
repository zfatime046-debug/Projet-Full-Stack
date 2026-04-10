import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

const Affectations = () => {
  const [data, setData] = useState([]);
  const [employes, setEmployes] = useState([]);
  const [phases, setPhases] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [form, setForm] = useState({
    employeId: "",
    phaseId: "",
    role: "",
    dateDebut: "",
    dateFin: "",
  });

  const fetchData = async () => {
    try {
      const res = await api.get("/affectations");
      setData(res.data);
    } catch (err) {
      toast.error("Erreur chargement");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    api.get("/employes").then((res) => setEmployes(res.data));
    api.get("/phases").then((res) => setPhases(res.data));
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editing) {
        await api.put(`/affectations/${editing.employeId}/${editing.phaseId}`, form);
        toast.success("Affectation modifiée");
      } else {
        await api.post("/affectations", form);
        toast.success("Affectation ajoutée");
      }
      setShowModal(false);
      setEditing(null);
      setForm({ employeId: "", phaseId: "", role: "", dateDebut: "", dateFin: "" });
      fetchData();
    } catch (err) {
      toast.error("Erreur");
    }
  };

  const handleDelete = async (employeId: number, phaseId: number) => {
    if (window.confirm("Supprimer cette affectation ?")) {
      await api.delete(`/affectations/${employeId}/${phaseId}`);
      toast.success("Supprimée");
      fetchData();
    }
  };

  const openEdit = (item: any) => {
    setEditing(item);
    setForm({
      employeId: item.employeId ?? item.id?.employeId,
      phaseId: item.phaseId ?? item.id?.phaseId,
      role: item.role || "",
      dateDebut: item.dateDebut?.split("T")[0] || "",
      dateFin: item.dateFin?.split("T")[0] || "",
    });
    setShowModal(true);
  };

  if (loading) return <div>Chargement...</div>;

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Affectations</h1>
        <button
          onClick={() => {
            setEditing(null);
            setForm({ employeId: "", phaseId: "", role: "", dateDebut: "", dateFin: "" });
            setShowModal(true);
          }}
          className="bg-indigo-600 text-white px-4 py-2 rounded"
        >
          + Ajouter
        </button>
      </div>

      <div className="bg-white rounded-xl shadow overflow-auto">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3">Employé / Phase</th>
              <th className="px-6 py-3">Date début</th>
              <th className="px-6 py-3">Date fin</th>
              <th className="px-6 py-3">Employé</th>
              <th className="px-6 py-3">Rôle</th>
              <th className="px-6 py-3">Actions</th>
            </tr>
          </thead>
          <tbody>
            {data.map((item: any, idx) => (
              <tr key={idx} className="border-t">
                <td className="px-6 py-4">
                  {item.employeId ?? item.id?.employeId} / {item.phaseId ?? item.id?.phaseId}
                </td>
                <td className="px-6 py-4">{item.dateDebut?.split("T")[0] || "-"}</td>
                <td className="px-6 py-4">{item.dateFin?.split("T")[0] || "-"}</td>
                <td className="px-6 py-4">
                  {item.employe?.nom} {item.employe?.prenom} ({item.employe?.matricule})
                </td>
                <td className="px-6 py-4">{item.profil?.libelle || item.role || "-"}</td>
                <td className="px-6 py-4 space-x-2">
                  <button onClick={() => openEdit(item)} className="text-blue-600">Modifier</button>
                  <button onClick={() => handleDelete(item.employeId ?? item.id?.employeId, item.phaseId ?? item.id?.phaseId)} className="text-red-600">Supprimer</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-xl w-96">
            <h2 className="text-xl font-bold mb-4">{editing ? "Modifier" : "Ajouter"} une affectation</h2>
            <form onSubmit={handleSubmit} className="space-y-3">
              <select className="w-full border p-2 rounded" value={form.employeId} onChange={e => setForm({ ...form, employeId: e.target.value })} required>
                <option value="">Choisir employé</option>
                {employes.map((e: any) => <option key={e.id} value={e.id}>{e.nom} {e.prenom}</option>)}
              </select>
              <select className="w-full border p-2 rounded" value={form.phaseId} onChange={e => setForm({ ...form, phaseId: e.target.value })} required>
                <option value="">Choisir phase</option>
                {phases.map((p: any) => <option key={p.id} value={p.id}>{p.libelle}</option>)}
              </select>
              <input className="w-full border p-2 rounded" placeholder="Rôle" value={form.role} onChange={e => setForm({ ...form, role: e.target.value })} />
              <input type="date" className="w-full border p-2 rounded" value={form.dateDebut} onChange={e => setForm({ ...form, dateDebut: e.target.value })} />
              <input type="date" className="w-full border p-2 rounded" value={form.dateFin} onChange={e => setForm({ ...form, dateFin: e.target.value })} />
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

export default Affectations;