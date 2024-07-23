package com.israr.israr_zaslavskaya_inga_voting_app;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.CandidateDao;
import com.israr.israr_zaslavskaya_inga_voting_app.dao.ElectionDao;
import com.israr.israr_zaslavskaya_inga_voting_app.dao.VoterDao;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Election;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import com.israr.israr_zaslavskaya_inga_voting_app.service.VoteService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//@SpringBootTest
//@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class RepositoryMethodsTest {

    @Autowired
    private CandidateDao candidateRepository;
    @Test
    public void testFindByRole() {
        // Save some candidates to the database for testing
        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
       candidate1.setId(1L);
       candidate2.setId(2L);
       candidate1.setRole("President");
       candidate2.setRole("President");
       candidate1.setName("John Doe");
       candidate2.setName("Jane Smith");
        candidateRepository.save(candidate1);
        candidateRepository.save(candidate2);

        // Perform the repository method call
        List<Candidate> foundCandidates = candidateRepository.findByRole("President");

        // Verify the result
        assertNotNull(foundCandidates);
        assertEquals(2, foundCandidates.size()); // Should contain 2 candidates with role "President"
    }
}
