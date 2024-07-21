package com.israr.israr_zaslavskaya_inga_voting_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "images")
public class AdminUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userProfilePic;
    private Long size;

    @Lob
    private byte [] content;

    @OneToOne(mappedBy = "adminUpload")
    private Candidate candidate;

}
