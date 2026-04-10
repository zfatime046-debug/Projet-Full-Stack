import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import toast from "react-hot-toast";

const Documents = () => {
  const [projets, setProjets] = useState<any[]>([]);
  const [docs, setDocs] = useState<any[]>([]);
  const [selectedProjet, setSelectedProjet] = useState<string>("");
  const [file, setFile] = useState<File | null>(null);
  const [titre, setTitre] = useState("");

  useEffect(() => {
    api.get("/projets")
      .then(res => setProjets(res.data))
      .catch(() => toast.error("Erreur projets"));
  }, []);

  const loadDocuments = async (projetId: number) => {
    try {
      const res = await api.get(`/projets/${projetId}/documents`);
      setDocs(res.data);
    } catch {
      toast.error("Erreur chargement documents");
    }
  };

  const handleUpload = async () => {
    if (!selectedProjet || !file) {
      toast.error("Sélectionne un projet et un fichier");
      return;
    }

    const formData = new FormData();

    // fichier
    formData.append("file", file);

    // DTO JSON
    formData.append(
      "data",
      new Blob(
        [
          JSON.stringify({
            titre: titre || file.name,
            type: file.type
          })
        ],
        { type: "application/json" }
      )
    );

    try {
      await api.post(`/projets/${selectedProjet}/documents`, formData);

      toast.success("Fichier uploadé");

      loadDocuments(Number(selectedProjet));
      setFile(null);
      setTitre("");
    } catch (err: any) {
      toast.error(err?.response?.data?.message || "Erreur upload");
    }
  };

  const download = async (id: number, nom: string) => {
    const res = await api.get(`/documents/${id}/download`, {
      responseType: "blob"
    });

    const url = window.URL.createObjectURL(new Blob([res.data]));
    const link = document.createElement("a");

    link.href = url;
    link.setAttribute("download", nom);
    document.body.appendChild(link);
    link.click();
  };

  return (
    <div className="p-6 space-y-6">

      <h1 className="text-2xl font-bold">Documents</h1>

      <select
        className="border p-2 rounded"
        value={selectedProjet}
        onChange={(e) => {
          setSelectedProjet(e.target.value);
          if (e.target.value) loadDocuments(Number(e.target.value));
        }}
      >
        <option value="">Choisir un projet</option>
        {projets.map((p: any) => (
          <option key={p.id} value={p.id}>
            {p.nom}
          </option>
        ))}
      </select>

      <div className="flex gap-4">
        <input
          type="text"
          placeholder="Titre"
          className="border p-2 rounded"
          value={titre}
          onChange={(e) => setTitre(e.target.value)}
        />

        <input
          type="file"
          onChange={(e) => setFile(e.target.files?.[0] || null)}
        />

        <button
          onClick={handleUpload}
          className="bg-indigo-600 text-white px-4 py-2 rounded"
        >
          Upload
        </button>
      </div>

      <div className="space-y-2">
        {docs.map((doc: any) => (
          <div key={doc.id} className="p-4 bg-white shadow flex justify-between">
            <div>
              <strong>{doc.titre}</strong>
              <p className="text-sm">{doc.type}</p>
            </div>

            <button
              onClick={() => download(doc.id, doc.titre)}
              className="text-blue-600"
            >
              Télécharger
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Documents;