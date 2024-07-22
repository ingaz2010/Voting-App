package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stateName;
    private boolean isSelected;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<County> counties;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<Voter> voters;

    @ManyToMany(mappedBy = "states")
    private List<Candidate> candidates = new ArrayList<>();
}

