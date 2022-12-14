package com.kadem.kadem.Services;

import com.kadem.kadem.Entities.*;
import com.kadem.kadem.ExceptionHandlingEtudiantContrat.InvalidExceptionEtudiantContrat;
import com.kadem.kadem.Repository.ContratRepository;
import com.kadem.kadem.Repository.DepartementRepository;
import com.kadem.kadem.Repository.EquipeRepository;
import com.kadem.kadem.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EtudiantService  implements EtudiantServiceInterface{
    @Autowired
    private EtudiantRepository etudiantRepo;

    @Autowired
    private DepartementRepository departementRepo;

    @Autowired
    private ContratRepository contratRepo;
    @Autowired
    private EquipeRepository equipeRepo;
    @Override
    public String addEtudiant(Etudiant E) {
        Etudiant etudiant=etudiantRepo.save(E);
        if(etudiant!=null)
            return "Etudiant Ajouté";
        else
            return "Etudiant non Ajouté";
    }

    @Override
    public List<Etudiant> GetAllEtudiants() {

        return (List<Etudiant>)etudiantRepo.findAll();
    }

    @Override
    public Etudiant updateEtudiant(Long Id, Etudiant E) {
        if (etudiantRepo.existsById(Id)) {
            Etudiant etudiant = etudiantRepo.findById(Id).get();
            etudiant.setNomE(E.getNomE());
            etudiant.setPrenomE(E.getPrenomE());
            etudiant.setEmail(E.getEmail());
            etudiant.setAge(E.getAge());
            etudiant.setAdresse(E.getAdresse());
            etudiant.setNumeroTelephone(E.getNumeroTelephone());
            etudiant.setNiveauEtudiant(E.getNiveauEtudiant());
            etudiant.setOption(E.getOption());
            etudiant.setClasse(E.getClasse());

            etudiantRepo.save(etudiant);
            return etudiant;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Etudiant GetEtudiantById(Long Id) {

        return etudiantRepo.findById(Id).get();
    }

    @Override
    public void DeleteEtudiant(Long Id) {
        etudiantRepo.deleteById(Id);

    }

    ///ajouter un etudiant avec son departement
    @Override
    public Etudiant assignEtudiantToDepartementWithNomD(Etudiant etudiant, String nomDepartement) throws InvalidExceptionEtudiantContrat {
        Departement  departement=departementRepo.findByNomDepart(nomDepartement);
        etudiant.setDepartement(departement);
        //if((etudiantRepo.findByEmail(etudiant.getEmail())!=null)&&(etudiant.getConfirmPassword()!=etudiant.getPassword()) ) throw new InvalidExceptionEtudiantContrat("essayer avec un autre mail et confirm password  ");
        //if (etudiantRepo.findByEmail(etudiant.getEmail())!=null) throw new InvalidExceptionEtudiantContrat("Ce Email existe deja! Essayer avec un autre");
        //if(etudiant.getConfirmPassword()!=etudiant.getPassword()) throw new InvalidExceptionEtudiantContrat("resaisir votre confirm password ");
        return etudiantRepo.save(etudiant);
    }


    //ajouter et affecter un étudiant à une équipe et un contrat en utilisant une seule méthode
    @Override
    public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Long idContrat, Long idEquipe) {
        Contrat contrat=contratRepo.findById(idContrat).get();
        Equipe equipe=equipeRepo.findById(idEquipe).get();
        Etudiant etudiant = etudiantRepo.save(e);
        contrat.setEtudiant(etudiant);
        if (equipe.getEtudiants() != null){
            equipe.getEtudiants().add(etudiant);
        }
        else{
            List<Etudiant> lE = new ArrayList<Etudiant>();
            lE.add(etudiant);
            equipe.setEtudiants(lE);

        }
        contratRepo.save(contrat);
        equipeRepo.save(equipe);

        return etudiant;
    }

   ////////trier les étudiants selon leur moyennes par classe/niveau et option
    @Override
    public List<Etudiant> getEtudiantOrderByMoyenne(String nomDepart, Option option, Integer niveau, Integer classe) {
        Departement departement=departementRepo.findByNomDepart(nomDepart);
        List<Etudiant> etudiants= etudiantRepo.findByDepartement_IdDepartAndClasseAndNiveauEtudiantAndOptionOrderByMoyenneEDesc(departement.getIdDepart(), classe,niveau,option);
        return etudiants;
    }

    /////afficher la liste des enseignants de chaque etudiants groupés par le nom du module
    @Override
    public List<Enseignant> getEnseignantsByIdEtudiantGroupByModule(Long idEtudiant) {

        return etudiantRepo.getEnseignantsByIdEtudiantGroupByModule(idEtudiant);
    }





}
