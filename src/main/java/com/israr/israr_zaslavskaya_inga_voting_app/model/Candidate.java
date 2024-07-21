package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String imageUrl;

    @ManyToMany(mappedBy = "candidates")
    private Set<County> countiesRepresents;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @OneToOne
    @JoinColumn(name = "image_id")
    private AdminUpload adminUpload;
}