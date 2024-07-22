//package com.israr.israr_zaslavskaya_inga_voting_app.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "images")
//public class AdminUpload {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String userProfilePic;
//    private Long size;
//    private String imageUrl;
//
//    @Lob
//    private byte [] content;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "candidate_id")
//    private Candidate candidate;
//
//}
