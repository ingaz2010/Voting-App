package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.County;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterDao extends JpaRepository<Voter, Long> {

    Voter findByEmail(String email);
    Voter findByIdNumber(String idNumber);
    Voter findBySsn(String ssn);

}
