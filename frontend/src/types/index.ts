export interface User {
  id: number;
  login: string;
  email: string;
  role: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  login: string;
  role: string;
  user: any;
}

export interface Organisme {
  id: number;
  code: string;
  nom: string;
  adresse?: string;
  telephone?: string;
  email?: string;
  personneContact?: string;
}

export interface Employe {
  id: number;
  matricule: string;
  nom: string;
  prenom: string;
  email: string;
  login: string;
  actif: boolean;
}

export interface Projet {
  id: number;
  code: string;
  nom: string;
  dateDebut: string;
  dateFin: string;
  montant: number;
}

export interface Phase {
  id: number;
  libelle: string;
  dateDebut: string;
  dateFin: string;
  montant: number;
  etatRealisation: boolean;
  etatFacturation: boolean;
  etatPaiement: boolean;
}

export interface Facture {
  id: number;
  code: string;
  montant: number;
  statut: string;
}