package com.israr.israr_zaslavskaya_inga_voting_app;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.*;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestRepositories {

//    @Test
//    void contextLoads() {
//    }
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private VoterDao voterDao;
    @Autowired
    private VoterChoiceDao voterChoiceDao;
    @Autowired
    private CountyDao countyDao;
    @Autowired
    private ElectionDao electionDao;
    @Autowired
    private RoleDao roleDao;
   // @Mock
    @Autowired
    private StateDao stateDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Testing List<Candidate> findByRole(String candidateRole) from CandidateDao;
    @ParameterizedTest
    @CsvSource({
            "Ann Smith, President, Independent, n/a",
            "Jane Doe, President, Democrat, N/A",
            "John Smith, President, Republican, N/a"
    })
    void testFindByRole(String name, String role, String party, String description){
        List<Candidate> candidates = new ArrayList<>();
        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setRole(role);
        candidate.setParty(party);
        candidate.setDescription(description);
        candidates.add(candidate);

        // Mock the behavior of candidateDao.findByRole("President")
        //when(candidateDao.findByRole("President")).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateDao.findByRole("President");
        // Assertions
        assertThat(foundCandidates).isNotNull();
        assertThat(foundCandidates.size()).isEqualTo(1);
        assertThat(foundCandidates.get(0).getName()).isEqualTo(name);
        assertThat(foundCandidates.get(0).getRole()).isEqualTo(role);
        assertThat(foundCandidates.get(0).getParty()).isEqualTo(party);
        assertThat(foundCandidates.get(0).getDescription()).isEqualTo(description);

    }

    //Testing  Voter findByEmail(String email) method from VoterDao
    @Test
    public void testFindByEmail() {
        // Create a Voter object
        Voter sampleVoter = new Voter();
        sampleVoter.setId(1L);
        sampleVoter.setFirstName("Jake");
        sampleVoter.setLastName("Doe");
        sampleVoter.setEmail("jake@mail.com");
        sampleVoter.setPhone("123-456-7890");
        sampleVoter.setIdNumber("123456789");
        sampleVoter.setGender("Male");
        sampleVoter.setAddress("123 Main St");
        sampleVoter.setCity("NYC");
        sampleVoter.setZip("12345");

        // Mock the behavior of voterDao.findByEmail("john.doe@example.com")
        //when(voterDao.findByEmail("jake@mail.com")).thenReturn(sampleVoter);

        // Call the method under test
        Voter foundVoter = voterDao.findByEmail("jake@mail.com");

        // Assertions
        assertThat(foundVoter).isNotNull();
        assertThat(foundVoter.getId()).isEqualTo(1L);
        assertThat(foundVoter.getFirstName()).isEqualTo("Jake");
        assertThat(foundVoter.getLastName()).isEqualTo("Doe");
        assertThat(foundVoter.getPhone()).isEqualTo("123-456-7890");
        assertThat(foundVoter.getIdNumber()).isEqualTo("123456789");
        assertThat(foundVoter.getGender()).isEqualTo("Male");
        assertThat(foundVoter.getAddress()).isEqualTo("123 Main St");
        assertThat(foundVoter.getCity()).isEqualTo("NYC");
        assertThat(foundVoter.getZip()).isEqualTo("12345");
    }

    @Test
    public void testFindAllByVoterId() {
    //Testing List<VoterChoice> findAllByVoterId(Long voterId) method from VoterChoiceDao
    Voter voter = new Voter();
        voter.setId(1L);

    Candidate candidate = new Candidate();
        candidate.setId(1L);

    Election election = new Election();
        election.setId(1L);

        Candidate candidate2 = new Candidate();
        candidate.setId(2L);

        Election election2 = new Election();
        election.setId(2L);
    VoterChoice voterChoice1 = new VoterChoice();
        voterChoice1.setId(1L);
        voterChoice1.setDate(LocalDate.now());
        voterChoice1.setCandidateOfChoice(1L);
        voterChoice1.setVoter(voter);
        voterChoice1.setCandidateSelected(candidate);
        voterChoice1.setElection(election);

    VoterChoice voterChoice2 = new VoterChoice();
        voterChoice2.setId(2L);
        voterChoice2.setDate(LocalDate.now());
        voterChoice2.setCandidateOfChoice(2L);
        voterChoice2.setVoter(voter);
        voterChoice2.setCandidateSelected(candidate);
        voterChoice2.setElection(election);

    List<VoterChoice> voterChoices = new ArrayList<>();
        voterChoices.add(voterChoice1);
        voterChoices.add(voterChoice2);

    // Mocking repository method
   // when(voterChoiceDao.findAllByVoterId(1L)).thenReturn(voterChoices);

    // Calling the method under test
    List<VoterChoice> foundVoterChoices = voterChoiceDao.findAllByVoterId(1L);

    // Assertions
    assertThat(foundVoterChoices).isNotNull();
    assertThat(foundVoterChoices).hasSize(2); // Assuming two VoterChoices are returned
    assertThat(foundVoterChoices.get(0).getId()).isEqualTo(1L);
    assertThat(foundVoterChoices.get(1).getId()).isEqualTo(2L);
}

    @AfterEach
    void tearDown() throws SQLException {
        candidateDao.deleteAll();
        voterDao.deleteAll();
    }

    //Testing County findByName(String countyName) method from CountyDao
    @Test
    public void testFindByName() {
        // Create a sample County
        County county = new County();
        county.setId(1L);
        county.setName("Sample County");

        // Mocking repository method
       // when(countyDao.findByName("Sample County")).thenReturn(county);

        // Calling the method under test
        County foundCounty = countyDao.findByName("Sample County");

        // Assertions
        assertThat(foundCounty).isNotNull(); // Ensure County is found
        assertThat(foundCounty.getId()).isEqualTo(1L);
        assertThat(foundCounty.getName()).isEqualTo("Sample County");
    }

    //Testing List<Election> findAllByPosition(String role);
    @Test
    public void testFindAllByPosition() {
        // Create sample Election entities
        Election election1 = new Election(1L, "President", true, "2024", new ArrayList<>(), new ArrayList<>());
        Election election2 = new Election(2L, "Governor", true, "2022", new ArrayList<>(), new ArrayList<>());
        Election election3 = new Election(3L, "Mayor", false, "2023", new ArrayList<>(), new ArrayList<>());

        List<Election> elections = new ArrayList<>();
        elections.add(election1);
        elections.add(election2);
        elections.add(election3);

        List<Election> presidentElection = new ArrayList<>();
        presidentElection.add(election1);

        // Mocking repository method
       // when(electionDao.findAllByPosition("President")).thenReturn(presidentElection);

        // Calling the method under test
        List<Election> foundElections = electionDao.findAllByPosition("President");

        // Assertions
        assertThat(foundElections).isNotNull();
        assertThat(foundElections).hasSize(1); // Assuming only one Election with position "President" is returned
        assertThat(foundElections.get(0).getId()).isEqualTo(1L);
        assertThat(foundElections.get(0).getPosition()).isEqualTo("President");
        assertThat(foundElections.get(0).getYear()).isEqualTo("2024");
        assertThat(foundElections.get(0).getIsActive()).isTrue();
    }

    // Test   List<Election> findByIsActive(boolean isActive);
    @Test
    public void testFindByIsActive() {
        // Create sample Election entities
        Election election1 = new Election(1L, "President", true, "2024", new ArrayList<>(), new ArrayList<>());
        Election election2 = new Election(2L, "Governor", false, "2022", new ArrayList<>(), new ArrayList<>());
        Election election3 = new Election(3L, "Mayor", true, "2023", new ArrayList<>(), new ArrayList<>());

        List<Election> elections = new ArrayList<>();
        elections.add(election1);
        elections.add(election3);

        // Mocking repository method
      //  when(electionDao.findByIsActive(true)).thenReturn(elections);

        // Calling the method under test
        List<Election> foundElections = electionDao.findByIsActive(true);

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

    //Test    Election findElectionByPosition(String name);
    @Test
    public void testFindByPosition() {
        // Sample data
        Election election1 = new Election(1L, "President", true, "2024", new ArrayList<>(), new ArrayList<>());
        Election election2 = new Election(2L, "Governor", false, "2022", new ArrayList<>(), new ArrayList<>());
        Election election3 = new Election(3L, "Mayor", true, "2023", new ArrayList<>(), new ArrayList<>());

        List<Election> elections = new ArrayList<>();
        elections.add(election1);
        elections.add(election3);

        // Mocking repository method
//        when(electionDao.findElectionByPosition("President")).thenReturn(election1);
//        when(electionDao.findElectionByPosition("Mayor")).thenReturn(election3);

        // Calling the method under test
        Election foundElection1 = electionDao.findElectionByPosition("President");
        Election foundElection2 = electionDao.findElectionByPosition("Governor"); // Assuming Governor does not exist

        // Assertions
        assertThat(foundElection1).isNotNull();
        assertThat(foundElection1.getId()).isEqualTo(1L);
        assertThat(foundElection1.getPosition()).isEqualTo("President");
        assertThat(foundElection1.getYear()).isEqualTo("2024");
        assertThat(foundElection1.getIsActive()).isTrue();

        assertThat(foundElection2).isNull(); // Asserting that Governor is not present
    }

    //Test Role findByName(String name) from RoleDao
    @Test
    public void testRoleFindByName() {
        // Sample data setup
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ADMIN");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("USER");

        // Save sample data to the database
        roleDao.save(role1);
        roleDao.save(role2);

        // Calling the repository method under test
        Role foundRole1 = roleDao.findByName("ADMIN");
        Role foundRole2 = roleDao.findByName("SuperAdmin"); // Assuming SuperAdmin does not exist

        // Assertions
        assertThat(foundRole1).isNotNull();
        assertThat(foundRole1.getId()).isEqualTo(1L);
        assertThat(foundRole1.getName()).isEqualTo("ADMIN");

        assertThat(foundRole2).isNull(); // Asserting that SuperAdmin is not present
    }

    //Test  State findByStateName(String stateName) from stateDao
    @Test
    public void testFindByStateName() {
        // Sample data setup
        State state1 = new State(1L, "California", true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        State state2 = new State(2L, "New York", true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Save sample data to the database
        stateDao.save(state1);
        stateDao.save(state2);

        // Calling the repository method under test
        State foundState1 = stateDao.findByStateName("California");
        State foundState2 = stateDao.findByStateName("Texas"); // Assuming Texas does not exist

        // Assertions
        assertThat(foundState1).isNotNull();
        assertThat(foundState1.getId()).isEqualTo(1L);
        assertThat(foundState1.getStateName()).isEqualTo("California");

        assertThat(foundState2).isNull(); // Asserting that Texas is not present
    }
}
