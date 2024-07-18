package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.VoterChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterChoiceDao extends JpaRepository<VoterChoice, Long> {
    //Long countByCandidate(Candidate candidate);
}
