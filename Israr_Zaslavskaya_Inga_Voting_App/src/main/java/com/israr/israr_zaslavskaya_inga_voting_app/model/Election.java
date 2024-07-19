package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
//    @JoinColumn(name = "candidate_id")
    private List<Candidate> candidates;

    @ManyToMany
    private List<County> counties;

    //    @OneToMany(targetEntity = Voter.class, cascade = CascadeType.ALL)
//    @ManyToMany(mappedBy = "elections")
//    private List<Voter> voters;
}

