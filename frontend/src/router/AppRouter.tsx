import React from "react";
import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Layout from "../components/Layout";
import Login from "../pages/Login";
import Dashboard from "../pages/Dashboard";
import Organismes from "../pages/Organismes";
import Employes from "../pages/Employes";
import Projets from "../pages/Projets";
import Phases from "../pages/Phases";
import Factures from "../pages/Factures";
import Affectations from "../pages/Affectations";
import Documents from "../pages/Documents";
import Livrables from "../pages/Livrables";

const AppRouter = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route element={<PrivateRoute />}>
        <Route element={<Layout />}>
          <Route path="/" element={<Dashboard />} />
          <Route path="/organismes" element={<Organismes />} />
          <Route path="/employes" element={<Employes />} />
          <Route path="/projets" element={<Projets />} />
          <Route path="/phases" element={<Phases />} />
          <Route path="/factures" element={<Factures />} />
          <Route path="/affectations" element={<Affectations />} />
<Route path="/documents" element={<Documents />} />
<Route path="/livrables" element={<Livrables />} />
        </Route>
      </Route>
    </Routes>
  );
};

export default AppRouter;
