package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.VoterChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoterChoiceDao extends JpaRepository<VoterChoice, Long> {
    List<VoterChoice> findAllByVoterId(Long voterId);
}
