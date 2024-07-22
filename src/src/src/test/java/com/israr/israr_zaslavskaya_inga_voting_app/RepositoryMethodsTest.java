package com.israr.israr_zaslavskaya_inga_voting_app;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.CandidateDao;
import com.israr.israr_zaslavskaya_inga_voting_app.dao.ElectionDao;
import com.israr.israr_zaslavskaya_inga_voting_app.dao.VoterDao;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Election;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
//@SpringBootTest
@DataJpaTest
public class RepositoryMethodsTest {

    @Autowired
    private ElectionDao electionRepository;

    @Test
    public void testFindByPosition() {
        // Sample data setup in the database
        Election election1 = new Election(1L, "President", true, "2024", null, null);
        Election election2 = new Election(2L, "Governor", false, "2022", null, null);
        Election election3 = new Election(3L, "Mayor", true, "2023", null, null);

        // Save sample data to the database
        electionRepository.save(election1);
        electionRepository.save(election2);
        electionRepository.save(election3);

        // Calling the repository method under test
        Election foundElection1 = electionRepository.findElectionByPosition("President");
        Election foundElection2 = electionRepository.findElectionByPosition("Governor");

        // Assertions
        assertThat(foundElection1).isNotNull();
        assertThat(foundElection1.getId()).isEqualTo(1L);
        assertThat(foundElection1.getPosition()).isEqualTo("President");
        assertThat(foundElection1.getYear()).isEqualTo("2024");
        assertThat(foundElection1.getIsActive()).isTrue();

        assertThat(foundElection2).isNull(); // Asserting that Governor is not present
    }

    @Test
    public void testFindByIsActive() {
        // Sample data setup in the database
        Election election1 = new Election(1L, "President", true, "2024", null, null);
        Election election2 = new Election(2L, "Governor", false, "2022", null, null);
        Election election3 = new Election(3L, "Mayor", true, "2023", null, null);

        // Save sample data to the database
        electionRepository.save(election1);
        electionRepository.save(election2);
        electionRepository.save(election3);

        // Calling the repository method under test
        List<Election> foundElections = electionRepository.findByIsActive(true);

        // Assertions
        assertThat(foundElections).isNotNull();
        assertThat(foundElections).hasSize(2); // Assuming two Elections with isActive=true are returned
        assertThat(foundElections.get(0).getId()).isEqualTo(1L);
        assertThat(foundElections.get(1).getId()).isEqualTo(3L);
        assertThat(foundElections.get(0).getPosition()).isEqualTo("President");
        assertThat(foundElections.get(1).getPosition()).isEqualTo("Mayor");
        assertThat(foundElections.get(0).getYear()).isEqualTo("2024");
        assertThat(foundElections.get(1).getYear()).isEqualTo("2023");
        assertThat(foundElections.get(0).getIsActive()).isTrue();
        assertThat(foundElections.get(1).getIsActive()).isTrue();
    }


}
