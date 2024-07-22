package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionDao extends JpaRepository<Election, Long> {
    List<Election> findAllByPosition(String role);
    List<Election> findByIsActive(boolean isActive);
    Election findElectionByPosition(String name);
}
