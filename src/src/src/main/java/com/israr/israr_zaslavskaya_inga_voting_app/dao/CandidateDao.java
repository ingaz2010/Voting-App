package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateDao extends JpaRepository<Candidate, Long> {

    List<Candidate> findByRole(String candidateRole);

}
