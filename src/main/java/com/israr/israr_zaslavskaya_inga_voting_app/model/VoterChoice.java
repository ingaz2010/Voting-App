package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class VoterChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Long candidateOfChoice;

    @ManyToOne
    @JoinColumn(name = "voters-id")
    Voter voter;

    @OneToOne
    Election election;

    @OneToOne
    Candidate candidateSelected;
}

