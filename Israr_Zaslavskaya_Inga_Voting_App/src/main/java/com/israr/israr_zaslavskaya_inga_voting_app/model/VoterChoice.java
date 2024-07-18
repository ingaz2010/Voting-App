package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoterChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Voter voter;

    @OneToOne
    Election election;

    @OneToOne
    Candidate candidateSelected;
}

