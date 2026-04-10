import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

type Employe = {
  id?: number;
  matricule: string;
  nom: string;
  prenom: string;
  email: string;
  login: string;
  password: string;
  telephone: string;
  profilId: number | "";
};

const Employes = () => {
  const [data, setData] = useState<Employe[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<Employe | null>(null);
  const [search, setSearch] = useState("");

  const [form, setForm] = useState<Employe>({
    matricule: "",
    nom: "",
    prenom: "",
    email: "",
    login: "",
    password: "",
    telephone: "",
    profilId: ""
  });

  const fetchData = async () => {
    try {
      const res = await api.get("/employes");
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

  const filtered = data.filter((item) =>
    `${item.nom} ${item.prenom} ${item.email} ${item.matricule}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // ✅ IMPORTANT : profilId doit être NUMBER ou NULL
      const payload = {
        ...form,
        profilId: form.profilId === "" ? null : Number(form.profilId)
      };

      if (!payload.profilId) {
        toast.error("Choisis un profil valide !");
        return;
      }

      if (editing) {
        await api.put(`/employes/${editing.id}`, payload);
        toast.success("Employé modifié");
      } else {
        await api.post("/employes", payload);
        toast.success("Employé ajouté");
      }

      setShowModal(false);
      setEditing(null);

      setForm({
        matricule: "",
        nom: "",
        prenom: "",
        email: "",
        login: "",
        password: "",
        telephone: "",
        profilId: ""
      });

      fetchData();
    } catch (err: any) {
      toast.error(err?.response?.data?.message || "Erreur serveur");
    }
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;

    if (window.confirm("Supprimer cet employé ?")) {
      await api.delete(`/employes/${id}`);
      toast.success("Supprimé");
      fetchData();
    }
  };

  const openEdit = (item: Employe) => {
    setEditing(item);

    setForm({
      matricule: item.matricule,
      nom: item.nom,
      prenom: item.prenom,
      email: item.email,
      login: item.login,
      password: "",
      telephone: item.telephone || "",
      profilId: item.profilId || ""
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
            setForm({
              matricule: "",
              nom: "",
              prenom: "",
              email: "",
              login: "",
              password: "",
              telephone: "",
              profilId: ""
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
          <thead>
            <tr>
              {columns.map((col) => (
                <th key={col} className="text-left px-4 py-2">
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

              <input placeholder="Matricule"
                className="w-full border p-2 rounded"
                value={form.matricule}
                onChange={e => setForm({ ...form, matricule: e.target.value })}
                required
              />

              <input placeholder="Nom"
                className="w-full border p-2 rounded"
                value={form.nom}
                onChange={e => setForm({ ...form, nom: e.target.value })}
                required
              />

              <input placeholder="Prénom"
                className="w-full border p-2 rounded"
                value={form.prenom}
                onChange={e => setForm({ ...form, prenom: e.target.value })}
                required
              />

              <input placeholder="Email"
                className="w-full border p-2 rounded"
                value={form.email}
                onChange={e => setForm({ ...form, email: e.target.value })}
                required
              />

              <input placeholder="Login"
                className="w-full border p-2 rounded"
                value={form.login}
                onChange={e => setForm({ ...form, login: e.target.value })}
                required
              />

              <input type="password"
                placeholder="Password"
                className="w-full border p-2 rounded"
                value={form.password}
                onChange={e => setForm({ ...form, password: e.target.value })}
                required
              />

              <input placeholder="Téléphone"
                className="w-full border p-2 rounded"
                value={form.telephone}
                onChange={e => setForm({ ...form, telephone: e.target.value })}
              />

              {/* IMPORTANT PROFIL */}
              <select
                className="w-full border p-2 rounded"
                value={form.profilId}
                onChange={e => setForm({ ...form, profilId: Number(e.target.value) })}
                required
              >
                <option value="">Choisir profil</option>
                <option value={1}>ADMIN</option>
                <option value={2}>CHEF</option>
                <option value={3}>DEV</option>
                <option value={4}>TEST</option>
                <option value={6}>SECRETAIRE</option>
                <option value={7}>DIRECTEUR</option>
                <option value={8}>COMPTABLE</option>
              </select>

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

export default Employes;