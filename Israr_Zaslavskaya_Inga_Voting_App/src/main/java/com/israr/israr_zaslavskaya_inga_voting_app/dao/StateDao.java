package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDao extends JpaRepository<State, Long> {
    State findByStateName(String stateName);
}
