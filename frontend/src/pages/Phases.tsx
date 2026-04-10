import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

type Projet = {
  id: number;
  nom: string;
  dateDebut: string;
  dateFin: string;
};

type Phase = {
  id?: number;
  code: string;
  libelle: string;
  description: string;
  dateDebut: string;
  dateFin: string;
  montant: number | string;
  projetId: number | "";
};

const Phases = () => {
  const [data, setData] = useState<Phase[]>([]);
  const [projets, setProjets] = useState<Projet[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<Phase | null>(null);
  const [search, setSearch] = useState("");

  const [form, setForm] = useState<Phase>({
    code: "",
    libelle: "",
    description: "",
    dateDebut: "",
    dateFin: "",
    montant: "",
    projetId: ""
  });

  useEffect(() => {
    fetchData();
    fetchProjets();
  }, []);

  const fetchData = async () => {
    try {
      const res = await api.get("/phases");
      setData(res.data);
    } catch {
      toast.error("Erreur chargement phases");
    } finally {
      setLoading(false);
    }
  };

  const fetchProjets = async () => {
    try {
      const res = await api.get("/projets");
      setProjets(res.data);
    } catch {
      toast.error("Erreur projets");
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const projet = projets.find(p => p.id === form.projetId);

    if (!projet) {
      toast.error("Choisis un projet");
      return;
    }

    // 🔥 VALIDATION DATES
    if (
      form.dateDebut < projet.dateDebut ||
      (form.dateFin && form.dateFin > projet.dateFin)
    ) {
      toast.error("Les dates doivent être dans la période du projet");
      return;
    }

    const payload = {
      ...form,
      projetId: Number(form.projetId),
      montant: Number(form.montant),
      dateFin: form.dateFin || null
    };

    try {
      if (editing) {
        await api.put(`/phases/${editing.id}`, payload);
        toast.success("Phase modifiée");
      } else {
        await api.post("/phases", payload);
        toast.success("Phase ajoutée");
      }

      setShowModal(false);
      setEditing(null);

      setForm({
        code: "",
        libelle: "",
        description: "",
        dateDebut: "",
        dateFin: "",
        montant: "",
        projetId: ""
      });

      fetchData();
    } catch (err: any) {
      toast.error(err?.response?.data?.message || "Erreur serveur");
    }
  };

  const openEdit = (item: any) => {
    setEditing(item);

    setForm({
      code: item.code,
      libelle: item.libelle,
      description: item.description || "",
      dateDebut: item.dateDebut?.split("T")[0],
      dateFin: item.dateFin?.split("T")[0] || "",
      montant: item.montant,
      projetId: item.projetId || ""
    });

    setShowModal(true);
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    if (window.confirm("Supprimer ?")) {
      await api.delete(`/phases/${id}`);
      toast.success("Supprimée");
      fetchData();
    }
  };

  const filtered = data.filter((item) =>
    `${item.code} ${item.libelle} ${item.description || ""}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  if (loading) return <div>Chargement...</div>;

  return (
    <div className="space-y-6">

      <div className="flex justify-between">
        <h1 className="text-2xl font-bold">Phases</h1>

        <button
          onClick={() => {
            setEditing(null);
            setForm({
              code: "",
              libelle: "",
              description: "",
              dateDebut: "",
              dateFin: "",
              montant: "",
              projetId: ""
            });
            setShowModal(true);
          }}
          className="bg-indigo-600 text-white px-4 py-2 rounded"
        >
          + Ajouter
        </button>
      </div>

      <input
        className="w-full border p-2 rounded"
        placeholder="Rechercher..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {/* TABLE */}
      <div className="bg-white shadow rounded">
        <table className="w-full">
          <thead>
            <tr>
              <th>Code</th>
              <th>Libellé</th>
              <th>Dates</th>
              <th>Montant</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((item) => (
              <tr key={item.id}>
                <td>{item.code}</td>
                <td>{item.libelle}</td>
                <td>{item.dateDebut} → {item.dateFin || "..."}</td>
                <td>{item.montant}</td>

                <td>
                  <button onClick={() => openEdit(item)} className="text-blue-600">
                    Modifier
                  </button>

                  <button onClick={() => handleDelete(item.id)} className="text-red-600 ml-2">
                    Supprimer
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* MODAL */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center">
          <div className="bg-white p-6 rounded w-96">

            <h2 className="font-bold mb-3">
              {editing ? "Modifier" : "Ajouter"}
            </h2>

            <form onSubmit={handleSubmit} className="space-y-2">

              <input className="w-full border p-2 rounded"
                placeholder="Code"
                value={form.code}
                onChange={(e) => setForm({ ...form, code: e.target.value })}
                required
              />

              <input className="w-full border p-2 rounded"
                placeholder="Libellé"
                value={form.libelle}
                onChange={(e) => setForm({ ...form, libelle: e.target.value })}
                required
              />

              {/* 🔥 PROJET DROPDOWN */}
              <select
                className="w-full border p-2 rounded"
                value={form.projetId}
                onChange={(e) =>
                  setForm({ ...form, projetId: Number(e.target.value) })
                }
                required
              >
                <option value="">-- Projet --</option>

                {projets.map((p) => (
                  <option key={p.id} value={p.id}>
                    {p.nom}
                  </option>
                ))}
              </select>

              <input type="date"
                className="w-full border p-2 rounded"
                value={form.dateDebut}
                onChange={(e) => setForm({ ...form, dateDebut: e.target.value })}
                required
              />

              <input type="date"
                className="w-full border p-2 rounded"
                value={form.dateFin}
                onChange={(e) => setForm({ ...form, dateFin: e.target.value })}
              />

              <input type="number"
                className="w-full border p-2 rounded"
                placeholder="Montant"
                value={form.montant}
                onChange={(e) => setForm({ ...form, montant: e.target.value })}
                required
              />

              <div className="flex justify-end gap-2">
                <button type="button"
                  onClick={() => setShowModal(false)}
                  className="border px-3 py-1 rounded">
                  Annuler
                </button>

                <button className="bg-indigo-600 text-white px-3 py-1 rounded">
                  Enregistrer
                </button>
              </div>

            </form>
          </div>
        </div>
      )}

    </div>
  );
};

export default Phases;