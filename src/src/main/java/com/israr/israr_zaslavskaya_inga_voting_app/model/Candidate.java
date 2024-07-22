package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private long id;
    private String name;
    private String role; //position
    private String party;
    private String description;
    //private String year;

    //private String imageUrl;

    @ManyToMany(mappedBy = "candidates")
    private Set<County> countiesRepresents;

    @ManyToMany
    @JoinTable(
            name = "candidates_states",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id"))
    private List<State> states;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
}