package com.israr.israr_zaslavskaya_inga_voting_app;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.CandidateDao;
import com.israr.israr_zaslavskaya_inga_voting_app.dao.VoterDao;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
public class RepositoryMethodsTest {




}
