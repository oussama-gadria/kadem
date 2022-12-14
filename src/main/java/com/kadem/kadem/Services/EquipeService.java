package com.kadem.kadem.Services;

import com.kadem.kadem.Entities.*;
import com.kadem.kadem.ExceptionHandling.InvalidIdException;
import com.kadem.kadem.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EquipeService implements IEquipeService {
    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private DetailEquipeRepository detailEquipeRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private EvenementRepository evenementRepository;
    @Override
    public List<Equipe> getListeEquipes(){
        return (List<Equipe>) equipeRepository.findAll();
    }
    @Override
    public Equipe getEquipeById(Long id){
        Equipe E=equipeRepository.findById(id).get();
        return E ;
    }
    @Override
    public Equipe addEquipe(Equipe E){
        return equipeRepository.save(E);
    }
    @Override
    public Equipe updateEquipe(Long id, Equipe E) {
        if(equipeRepository.existsById(id))
        {
        Equipe e1=equipeRepository.findById(id).get();
        e1.setNomEquipe(E.getNomEquipe());
        e1.setScore(E.getScore());
        e1.setNiveau(E.getNiveau());
        e1.setResponsable(E.getResponsable());
        equipeRepository.save(e1);
        return e1;
        }
        else return null;
    }

    @Override
    public String deleteEquipe(Long id) {

        if (equipeRepository.existsById(id)) {
            equipeRepository.deleteById(id);
            return "true";
        }
        else {
            return "false";
        }
    }

    @Override
    public Equipe addEquipeWithDetailEquipe(int IdDE, Equipe E){
        DetailEquipe detailEquipe=detailEquipeRepository.findById(IdDE).get();
        E.setDetailEquipe(detailEquipe);
        equipeRepository.save(E);
        return E;
    }

    @Override
    public Equipe assignEtudiantToEquipe(String prenom, String nom, Long idEquipe) {
        Etudiant etudiant=etudiantRepository.getEtudiantByNomAndPrenom(prenom,nom);
        Equipe equipe=equipeRepository.findById(idEquipe).get();
        List<Etudiant> etudiants=equipe.getEtudiants();
        etudiants.add(etudiant);
        equipeRepository.save(equipe);
        return equipe;
    }

    @Override
    public Equipe assignResponsableToEquipe(Long idEquipe,Long idEnseignant) throws InvalidIdException{
        Optional enseignant=enseignantRepository.findById(idEnseignant);
        Equipe equipe=equipeRepository.findById(idEquipe).get();
        if(enseignant.isPresent()){
        equipe.setResponsable(idEnseignant);
        equipeRepository.save(equipe);
        return equipe;}
        else{
            throw new InvalidIdException("Acun enseignant existe avec l'Id:"+idEnseignant);
        }
    }

    @Override
    public long getNbEtudiantsInEquipeWithOption(Long idEquipe, String option) {
        return equipeRepository.findById(idEquipe).get().getEtudiants()
                .stream().filter(e->e.getOption().toString().equals(option)).count();
    }

    @Override
    public int getNbEtudiantsInEquipeWithNomDepartement(Long idEquipe, String nomDepartement) {
        return (int) equipeRepository.findById(idEquipe).get().getEtudiants()
                .stream().filter(e->e.getDepartement().getNomDepart().equals(nomDepartement)).count();
    }

    @Override
    public List<Equipe> triEquipeByScore(Long idEvenement) {
        Evenement event=evenementRepository.findById(idEvenement).get();
        List<Equipe> ListEquipes=event.getEquipes();
        Integer taille=ListEquipes.size();
        for (Integer i=1 ;i<taille;i++)
        {
            Long score=ListEquipes.get(i).getScore();
            Integer j=i-1;
            while(j >= 0 && ListEquipes.get(j).getScore() > score)
            {
                ListEquipes.set(j+1,ListEquipes.get(j));
                j--;
            }
            ListEquipes.get(j+1).setScore(score);
        }

        return ListEquipes;
    }

    @Override
    public Equipe addEquipeWithResponsable(long IdEnseignant, Equipe E) {
        Enseignant enseignant=enseignantRepository.findById( IdEnseignant).get();
        Long id=enseignant.getIdEnseignant();
        E.setResponsable(id);
        equipeRepository.save(E);
        return E;
    }


}
