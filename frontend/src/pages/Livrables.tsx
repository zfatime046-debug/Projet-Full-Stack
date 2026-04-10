import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";

const Livrables = () => {
  const [phases, setPhases] = useState([]);
  const [livrables, setLivrables] = useState([]);
  const [selectedPhase, setSelectedPhase] = useState("");

  useEffect(() => {
    api.get("/phases").then(res => setPhases(res.data));
  }, []);

  const loadLivrables = (phaseId: number) => {
    api.get(`/phases/${phaseId}/livrables`)
      .then(res => setLivrables(res.data))
      .catch(err => console.error(err));
  };

  return (
    <div className="p-6 space-y-6">
      <h1 className="text-2xl font-bold">Livrables par phase</h1>

      <select
        className="border rounded-lg p-2"
        value={selectedPhase}
        onChange={e => {
          setSelectedPhase(e.target.value);
          loadLivrables(Number(e.target.value));
        }}
      >
        <option value="">Sélectionner une phase</option>
        {phases.map((p: any) => (
          <option key={p.id} value={p.id}>{p.libelle}</option>
        ))}
      </select>

      {livrables.length === 0 && selectedPhase && <p>Aucun livrable</p>}

      <div className="space-y-2">
        {livrables.map((liv: any) => (
          <div key={liv.id} className="bg-white p-4 rounded-xl shadow border">
            <p><strong>{liv.libelle}</strong> - {liv.code}</p>
            <p className="text-sm text-gray-500">{liv.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Livrables;