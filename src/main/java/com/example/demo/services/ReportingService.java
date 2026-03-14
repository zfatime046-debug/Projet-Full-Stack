package com.example.demo.services;

import com.example.demo.dto.TableauDeBordDTO;
import com.example.demo.entities.Phase;
import com.example.demo.entities.Projet;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface ReportingService {

    List<Phase> getPhasesTermineesNonFacturees();

    List<Phase> getPhasesFactureesNonPayees();

    List<Phase> getPhasesPayees();

    List<Projet> getProjetsEnCours();

    List<Projet> getProjetsClotures();

    TableauDeBordDTO getTableauDeBord();
    List<Projet> getProjetsEnCoursByChef(Long chefProjetId);
    List<Phase>getPhasesTermineesNonFactureesByProjet(Long ProjetId);
    List<Phase> getPhasesFactureesNonPayeesByProjet(Long projetId);
    List<Phase> getPhasesPayeesByProjet(Long projetId);
    List<Projet> getProjetsCloturesByChef(Long chefProjetId);
    List<Projet> getProjetsEnCoursByPeriode(LocalDate dateDebut, LocalDate dateFin);
    List<Projet> getProjetsCloturesByPeriode(LocalDate dateDebut, LocalDate dateFin);
    Page<Phase> getPhasesTermineesNonFactureesPage(int page, int size);
    Page<Phase> getPhasesFactureesNonPayeesPage(int page, int size);
    Page<Phase> getPhasesPayeesPage(int page, int size);
}
