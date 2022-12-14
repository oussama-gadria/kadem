package com.kadem.kadem.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity

@Table( name = "Equipe")

public class Equipe implements Serializable {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idEquipe;

    private String nomEquipe;

    private Long score;

    private Long responsable;

    @Enumerated(EnumType.STRING)

    private Niveau niveau;
    @JsonIgnoreProperties("equipes")
    @ManyToOne(cascade = CascadeType.ALL)
    Enseignant enseignant;
    @OneToOne
    private DetailEquipe detailEquipe;
    @JsonIgnoreProperties("equipes")
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Etudiant> etudiants;

    @JsonIgnoreProperties("equipes")
    @ManyToMany(cascade = CascadeType.ALL, mappedBy="equipes")
    private List<Evenement> evenements;


}
