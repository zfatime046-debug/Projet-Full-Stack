package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

@SpringBootTest
public class RepositoryTest extends AbstractTestNGSpringContextTests {

    ProjetRepository projetRepository;
    @Autowired EmployeRepository employeRepository;
    @Autowired PhaseRepository phaseRepository;
    @Autowired OrganismeRepository organismeRepository;
    @Autowired ProfilRepository profilRepository;
    @Autowired AffectationRepository affectationRepository;

    // Recherche projet par code
    @Test
    void testFindProjetByCode() {
        // Créer un projet de test
        Organisme org = new Organisme();
        org.setCode("ORG01");
        org.setNom("Organisme Test");
        organismeRepository.save(org);

        Projet projet = new Projet();
        projet.setCode("PRJ001");
        projet.setNom("Projet Test");
        projet.setDateDebut(LocalDate.now());
        projet.setDateFin(LocalDate.now().plusMonths(6));
        projet.setOrganisme(org);
        projetRepository.save(projet);

        // Tester la recherche
        Projet trouve = projetRepository.findByCode("PRJ001").orElse(null);
        assert trouve != null;
        assert trouve.getNom().equals("Projet Test");
        System.out.println("✅ Test 1 OK — Projet trouvé : " + trouve.getNom());
    }

    // Recherche employé par matricule
    @Test
    void testFindEmployeByMatricule() {
        Profil profil = new Profil();
        profil.setCode("CP");
        profil.setLibelle("Chef de projet");
        profilRepository.save(profil);

        Employe emp = new Employe();
        emp.setMatricule("EMP001");
        emp.setNom("Alami");
        emp.setPrenom("Samir");
        emp.setLogin("alami");
        emp.setEmail("alami@gmail.com");
        emp.setProfil(profil);
        employeRepository.save(emp);

        Employe trouve = employeRepository.findByMatricule("EMP001").orElse(null);
        assert trouve != null;
        assert trouve.getNom().equals("Alami");
        System.out.println("✅ Test 2 OK — Employé trouvé : " + trouve.getNom());
    }

    //Recherche phases d'un projet
    @Test
    void testFindPhasesByProjet() {
        Organisme org = new Organisme();
        org.setCode("ORG02");
        org.setNom("Organisme 2");
        organismeRepository.save(org);

        Projet projet = new Projet();
        projet.setCode("PRJ002");
        projet.setNom("Projet 2");
        projet.setDateDebut(LocalDate.now());
        projet.setDateFin(LocalDate.now().plusMonths(3));
        projet.setOrganisme(org);
        projetRepository.save(projet);

        Phase phase = new Phase();
        phase.setCode("PH001");
        phase.setLibelle("Phase 1");
        phase.setDateDebut(LocalDate.now());
        phase.setDateFin(LocalDate.now().plusMonths(1));
        phase.setProjet(projet);
        phaseRepository.save(phase);

        var phases = phaseRepository.findByProjetId(projet.getId());
        assert !phases.isEmpty();
        System.out.println("✅ Test 3 OK — Nombre de phases : " + phases.size());
    }

    //Phases terminées non facturées
    @Test
    void testPhasesTermineesNonFacturees() {
        Organisme org = new Organisme();
        org.setCode("ORG03");
        org.setNom("Organisme 3");
        organismeRepository.save(org);

        Projet projet = new Projet();
        projet.setCode("PRJ003");
        projet.setNom("Projet 3");
        projet.setDateDebut(LocalDate.now());
        projet.setDateFin(LocalDate.now().plusMonths(3));
        projet.setOrganisme(org);
        projetRepository.save(projet);

        Phase phase = new Phase();
        phase.setCode("PH002");
        phase.setLibelle("Phase terminée");
        phase.setDateDebut(LocalDate.now());
        phase.setDateFin(LocalDate.now().plusMonths(1));
        phase.setEtatRealisation(true);   // terminée
        phase.setEtatFacturation(false);  // pas encore facturée
        phase.setProjet(projet);
        phaseRepository.save(phase);

        var phases = phaseRepository
                .findByEtatRealisationTrueAndEtatFacturationFalse();
        assert !phases.isEmpty();
        System.out.println("✅ Test 4 OK — Phases terminées non facturées : "
                + phases.size());
    }

    //Phases facturées non payées
    @Test
    void testPhasesFactureesNonPayees() {
        Organisme org = new Organisme();
        org.setCode("ORG04");
        org.setNom("Organisme 4");
        organismeRepository.save(org);

        Projet projet = new Projet();
        projet.setCode("PRJ004");
        projet.setNom("Projet 4");
        projet.setDateDebut(LocalDate.now());
        projet.setDateFin(LocalDate.now().plusMonths(3));
        projet.setOrganisme(org);
        projetRepository.save(projet);

        Phase phase = new Phase();
        phase.setCode("PH003");
        phase.setLibelle("Phase facturée");
        phase.setDateDebut(LocalDate.now());
        phase.setDateFin(LocalDate.now().plusMonths(1));
        phase.setEtatRealisation(true);
        phase.setEtatFacturation(true);   // facturée
        phase.setEtatPaiement(false);     // pas encore payée
        phase.setProjet(projet);
        phaseRepository.save(phase);

        var phases = phaseRepository
                .findByEtatFacturationTrueAndEtatPaiementFalse();
        assert !phases.isEmpty();
        System.out.println("✅ Test 5 OK — Phases facturées non payées : "
                + phases.size());
    }
}