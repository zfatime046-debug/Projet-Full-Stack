import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

type Projet = {
  id?: number;
  code: string;
  nom: string;
  description: string;
  dateDebut: string;
  dateFin: string | null;
  montant: number | string;
  organismeId: number | "";
};

const Projets = () => {
  const [data, setData] = useState<Projet[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<Projet | null>(null);
  const [search, setSearch] = useState("");

  const [form, setForm] = useState<Projet>({
    code: "",
    nom: "",
    description: "",
    dateDebut: "",
    dateFin: null,
    montant: "",
    organismeId: ""
  });

  // ================= FETCH =================
  const fetchData = async () => {
    try {
      const res = await api.get("/projets");
      setData(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      toast.error("Erreur chargement");
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  // ================= SEARCH =================
  const filtered = data.filter((item) =>
    `${item.code} ${item.nom} ${item.description || ""}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  // ================= SUBMIT =================
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const payload = {
        code: form.code,
        nom: form.nom,
        description: form.description,
        dateDebut: form.dateDebut,
        dateFin:
          form.dateFin === "" || form.dateFin === null ? null : form.dateFin,
        montant: Number(form.montant),
        organismeId: form.organismeId ? Number(form.organismeId) : null
      };

      if (editing) {
        await api.put(`/projets/${editing.id}`, payload);
        toast.success("Projet modifié");
      } else {
        await api.post("/projets", payload);
        toast.success("Projet ajouté");
      }

      setShowModal(false);
      setEditing(null);

      setForm({
        code: "",
        nom: "",
        description: "",
        dateDebut: "",
        dateFin: null,
        montant: "",
        organismeId: ""
      });

      fetchData();
    } catch (err: any) {
      toast.error(err?.response?.data?.message || "Erreur serveur");
    }
  };

  // ================= DELETE =================
  const handleDelete = async (id?: number) => {
    if (!id) return;

    if (window.confirm("Supprimer ce projet ?")) {
      await api.delete(`/projets/${id}`);
      toast.success("Supprimé");
      fetchData();
    }
  };

  // ================= EDIT =================
  const openEdit = (item: any) => {
    setEditing(item);

    setForm({
      code: item.code,
      nom: item.nom,
      description: item.description || "",
      dateDebut: item.dateDebut?.split("T")[0] || "",
      dateFin: item.dateFin?.split("T")[0] || null,
      montant: item.montant,
      organismeId: item.organismeId || ""
    });

    setShowModal(true);
  };

  if (loading) return <div>Chargement...</div>;

  const columns = data[0] ? Object.keys(data[0]) : [];

  return (
    <div className="space-y-6">

      {/* HEADER */}
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Projets</h1>

        <button
          onClick={() => {
            setEditing(null);
            setForm({
              code: "",
              nom: "",
              description: "",
              dateDebut: "",
              dateFin: null,
              montant: "",
              organismeId: ""
            });
            setShowModal(true);
          }}
          className="bg-indigo-600 text-white px-4 py-2 rounded"
        >
          + Ajouter
        </button>
      </div>

      {/* SEARCH */}
      <input
        className="w-full border p-2 rounded"
        placeholder="Rechercher..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {/* TABLE */}
      <div className="bg-white rounded-xl shadow overflow-auto">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              {columns.map((col) => (
                <th key={col} className="px-4 py-2 text-left">
                  {col}
                </th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((item) => (
              <tr key={item.id} className="border-t">

                {columns.map((col) => (
                  <td key={col} className="px-4 py-2">
                    {String((item as any)[col] ?? "")}
                  </td>
                ))}

                <td className="px-4 py-2">
                  <button
                    onClick={() => openEdit(item)}
                    className="text-blue-600 mr-2"
                  >
                    Modifier
                  </button>

                  <button
                    onClick={() => handleDelete(item.id)}
                    className="text-red-600"
                  >
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

            <h2 className="text-lg font-bold mb-3">
              {editing ? "Modifier" : "Ajouter"}
            </h2>

            <form onSubmit={handleSubmit} className="space-y-2">

              <input
                placeholder="Code"
                className="w-full border p-2 rounded"
                value={form.code}
                onChange={e => setForm({ ...form, code: e.target.value })}
                required
              />

              <input
                placeholder="Nom"
                className="w-full border p-2 rounded"
                value={form.nom}
                onChange={e => setForm({ ...form, nom: e.target.value })}
                required
              />

              {/* ORGANISME ID */}
              <input
                type="number"
                placeholder="Organisme ID"
                className="w-full border p-2 rounded"
                value={form.organismeId}
                onChange={(e) =>
                  setForm({ ...form, organismeId: Number(e.target.value) })
                }
                required
              />

              <input
                placeholder="Description"
                className="w-full border p-2 rounded"
                value={form.description}
                onChange={(e) =>
                  setForm({ ...form, description: e.target.value })
                }
              />

              <input
                type="date"
                className="w-full border p-2 rounded"
                value={form.dateDebut}
                onChange={(e) =>
                  setForm({ ...form, dateDebut: e.target.value })
                }
                required
              />

              {/* DATE FIN OPTIONNELLE */}
              <input
                type="date"
                className="w-full border p-2 rounded"
                value={form.dateFin || ""}
                onChange={(e) =>
                  setForm({
                    ...form,
                    dateFin: e.target.value === "" ? null : e.target.value
                  })
                }
              />

              <label className="flex items-center gap-2 text-sm">
                <input
                  type="checkbox"
                  checked={!form.dateFin}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      dateFin: e.target.checked ? null : ""
                    })
                  }
                />
                Projet en cours
              </label>

              <input
                type="number"
                placeholder="Montant"
                className="w-full border p-2 rounded"
                value={form.montant}
                onChange={(e) =>
                  setForm({ ...form, montant: e.target.value })
                }
                required
              />

              <div className="flex justify-end gap-2">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="border px-3 py-1 rounded"
                >
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

export default Projets;