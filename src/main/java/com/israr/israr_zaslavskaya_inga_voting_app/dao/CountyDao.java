package com.israr.israr_zaslavskaya_inga_voting_app.dao;

import com.israr.israr_zaslavskaya_inga_voting_app.model.County;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountyDao extends JpaRepository<County, Long> {
    County findByName(String countyName);
}
