package com.kadem.kadem.Entities;


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

    @Enumerated(EnumType.STRING)

    private Niveau niveau;

    @OneToOne
    private DetailEquipe detailEquipe;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Etudiant> etudiants;

}
