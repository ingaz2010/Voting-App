package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String position;

    @Column(name = "is_active")
    private Boolean isActive;
    private String year;

    @OneToMany(targetEntity = Candidate.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private List<Candidate> candidates;

    @ManyToMany
    private List<County> counties;

    //    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    @ManyToMany(mappedBy = "elections")
//    private List<Voter> voters;
}

