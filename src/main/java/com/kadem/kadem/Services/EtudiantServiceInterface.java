package com.kadem.kadem.Services;

import com.kadem.kadem.Entities.Contrat;
import com.kadem.kadem.Entities.Enseignant;
import com.kadem.kadem.Entities.Etudiant;
import com.kadem.kadem.Entities.Option;
import com.kadem.kadem.ExceptionHandlingEtudiantContrat.InvalidExceptionEtudiantContrat;

import java.util.List;

public interface EtudiantServiceInterface {


    ////CRUD ETUDIANT
    String addEtudiant(Etudiant E);

    List<Etudiant> GetAllEtudiants();

    Etudiant updateEtudiant(Long Id, Etudiant E);

    Etudiant GetEtudiantById(Long Id);

    void DeleteEtudiant(Long Id);


    Etudiant assignEtudiantToDepartementWithNomD(Etudiant etudiant, String nomDepartement) throws InvalidExceptionEtudiantContrat;


    //ajouter et affecter un étudiant à une équipe et un contrat en utilisant une seule méthode
    Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Long idContrat, Long idEquipe);

    ////////trier les étudiants selon leur moyennes par classe/niveau et option
    List<Etudiant> getEtudiantOrderByMoyenne(String nomDepart, Option option, Integer niveau, Integer classe);

    /////afficher la liste des enseignants de chaque etudiants groupés par le nom du module

    List<Enseignant> getEnseignantsByIdEtudiantGroupByModule(Long idEtudiant);
}