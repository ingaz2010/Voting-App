package com.israr.israr_zaslavskaya_inga_voting_app.service;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.*;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VoteServiceImpl implements VoteService{
    private final CountyDao countyDao;
    private VoterChoiceDao voterChoiceDao;
    private VoterDao voterDao;
    private StateDao stateDao;
    private CandidateDao candidateDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;
    private ElectionDao electionDao;


    public VoteServiceImpl(VoterDao voterDao, CandidateDao candidateDao, RoleDao roleDao,
                           ElectionDao electionDao, PasswordEncoder passwordEncoder, /*HttpSession httpSession*/ VoterChoiceDao voterChoiceDao,
                           CountyDao countyDao, StateDao stateDao) {
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.roleDao = roleDao;
        this.electionDao = electionDao;
        this.passwordEncoder = passwordEncoder;
       // this.httpSession = httpSession;
        this.voterChoiceDao = voterChoiceDao;
        this.countyDao = countyDao;
        this.stateDao = stateDao;
    }

    @Override
    public void saveVoter(VoterDto voterDto) {
        Voter voter = new Voter();
        convertDtoToVoter(voterDto, voter);

        String roleName;
        if(voterDto.isAdminRegistration()){
            roleName = "ROLE_ADMIN";
        } else{
            roleName = "ROLE_USER";
        }

        Role role = roleDao.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName(roleName);
            roleDao.save(role);
        }

        voter.setRoles(Collections.singletonList(role));
        voterDao.save(voter);
    }

    @Override
    public void saveCandidate(CandidateDto candidateDto) {
        Candidate candidate = new Candidate();
        candidate.setId(candidateDto.getId());
        candidate.setName(candidateDto.getName());
        candidate.setRole(candidateDto.getRole());
        candidate.setParty(candidateDto.getParty());
        candidate.setDescription(candidateDto.getDescription());
        if(findCandidateByName(candidateDto.getName()) == null) {
            candidateDao.save(candidate);
        }

    }

    @Override
    public Voter findVoterByIdNumber(String idNumber) {
        return voterDao.findByIdNumber(idNumber);
    }

    @Override
    public Voter findVoterByEmail(String email) {
        return voterDao.findByEmail(email);
    }

    @Override
    public Candidate findCandidateByName(String name) {
        List<Candidate> candidates = candidateDao.findAll();
        for (Candidate candidate : candidates) {
            if(candidate.getName().equals(name)){
                return candidate;
            }
        }
        return null;
    }

    @Override
    public List<Candidate> findCandidatesByRole(String candidateRole) {
        List<Candidate> candidates = candidateDao.findByRole(candidateRole);
        return candidates;
    }

    @Override
    public void saveElection(ElectionDto electiondto) {
        Election election = new Election();
        election.setId(electiondto.getId());
        election.setPosition(electiondto.getPosition());
        election.setIsActive(electiondto.getIsActive());
        election.setYear(electiondto.getYear());
        List<Candidate> candidates = candidateDao.findByRole(electiondto.getPosition());
        election.setCandidates(candidates);
        electionDao.save(election);
    }

    @Override
    public void vote(Long id, HttpSession session) {
        Voter voter = (Voter) session.getAttribute("voter");
        if(!voter.isVoted()){
            voter.setVoted(true);
            Candidate candidate = candidateDao.findById(id).get();
            candidate.setVotes(candidate.getVotes()+1);
            candidateDao.save(candidate);
            voterDao.save(voter);
        }
    }

    @Override
    public Candidate getCandidateByName(String name) {
        return candidateDao.findByName(name);
    }

    @Override
    public Voter findVoterById(Long voterId) {
        return voterDao.findById(voterId).orElseThrow(() -> new NoSuchElementException("Not Found"));
    }

    @Override
    public void saveVoterChoice(VoterChoice voterChoice) {
        voterChoiceDao.save(voterChoice);
    }

    @Override
    public List<VoterChoice> findVoterChoicesByVoterEmail(String username) {
        Voter voter = voterDao.findByEmail(username);
        Long voterId = voter.getId();
        List<VoterChoice> choices = new ArrayList<>();
        List<VoterChoice> allChoices = voterChoiceDao.findAll();
        for (VoterChoice voterChoice : allChoices) {
            if(voterChoice.getVoter().getId() == voterId){
                choices.add(voterChoice);
            }
        }
        return choices;
    }

    @Override
    public List<VoterChoice> findAllVoterChoices() {
        return voterChoiceDao.findAll();
    }

    @Override
    public Long getVotesByCandidate(String name) {
        Candidate candidate = candidateDao.findByName(name);
        List<VoterChoice> votedForCandidate= new ArrayList<>();
        List<VoterChoice> allVoterChoices = voterChoiceDao.findAll();
        for (VoterChoice voterChoice : allVoterChoices) {
            if(voterChoice.getCandidateSelected().getId() == candidate.getId()){
                votedForCandidate.add(voterChoice);
            }
        }
        //Long count = voterChoiceDao.countByCandidate(candidate);
        return (long) votedForCandidate.size();
    }

    @Override
    public Election findActiveElectionByPosition(String electionName) {
        List<Election> electionsByPosition = electionDao.findAllByPosition(electionName);

      for (Election election : electionsByPosition) {
          if (election.getIsActive()) {
              return election;
          }
      }
            return null;
    }

        @Override
        public void saveELection (Election election){
            electionDao.save(election);
        }

        @Override
        public void findOrCreateCandidate (CandidateDto candidate, BindingResult bindingResult, Model model){
            Candidate existing = findCandidateByName(candidate.getName());
            if (existing != null) {
                bindingResult.rejectValue("name", null, "Candidate with this Name already exists");
            } else {
                existing = new Candidate();
                existing.setName(candidate.getName());
                existing.setRole(candidate.getRole());
                existing.setParty(candidate.getParty());
                existing.setDescription(candidate.getDescription());

            }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("candidate", candidate);
//            return "register-candidate";
//        }
            String electionName = candidate.getRole();
            System.out.println("Election " + electionName);
            Election election = findActiveElectionByPosition(electionName);
            //System.out.println("Adding candidate to election " + election);
            if (election == null) {
                System.out.println("Election " + electionName + " not found");
                election = new Election();
                election.setPosition(electionName);
                election.setYear(Year.now().toString());
                election.setIsActive(true);
                List<Candidate> candidates = new ArrayList<>();
                candidates.add(existing);
                election.setCandidates(candidates);
                System.out.println(election.getCandidates());
                electionDao.save(election);
                saveELection(election);
                existing.setElection(election);
                candidateDao.save(existing);
            } else {
                existing.setElection(election);
                System.out.println("Adding candidate to election " + election);
                existing.setElection(election);
                candidateDao.save(existing);
                System.out.println(election.getCandidates());
                List<Candidate> candidates = election.getCandidates();
                //existing.setElection(election);
                candidates.add(existing);
                System.out.println(candidates + " edited");
                //election.getCandidates().add(existing);
//                election.setCandidates(candidates);
//                saveELection(election);
//                existing.setElection(election);
//                candidateDao.save(existing);
                election.setCandidates(candidates);
                electionDao.save(election);
            }
        }

    @Override
    public List<County> getCounties() {
        List<County> counties = countyDao.findAll();
        return counties;
    }

    @Override
    public void createVoter(VoterDto voterdto, BindingResult bindingResult, Model model) {
        Voter search = findVoterByEmail(voterdto.getEmail());
        if (search == null) {
           Voter voter = new Voter();
           convertDtoToVoter(voterdto, voter);

           County county = countyDao.findByName(voterdto.getCounty());
           if (county == null) {
               county = new County();
               county.setName(voterdto.getCounty());
               county.getVoters().add(voter);
               //county.setState(voterdto.getState());
//               countyDao.save(county);
           }else{
               county.getVoters().add(search);
           }
           countyDao.save(county);

           State state = stateDao.findByStateName(voterdto.getState());
           if(state == null){
               state = new State();
               state.setStateName(voterdto.getState());
               state.setCounties(new ArrayList<County>());
               state.getCounties().add(county);
               county.setState(state);
               //state.getCounties().add(county);
               //state.getVoters().add(voter);
               state.setVoters(new ArrayList<Voter>());
               state.getVoters().add(voter);
           }else{
               County existingCounty = null;
               for(County c : state.getCounties()){
                   if(c.equals(county)){
                       existingCounty = c;
                   }
               }
               if(existingCounty == null){
                   state.getCounties().add(county);
               }
           }
           stateDao.save(state);

           voter.setCounty(county);
           voter.setState(state);
            voterDao.save(voter);
        }else{
         convertDtoToVoter(voterdto, search);

        voterDao.save(search);}
    }

    @Override
    public void displayVoterInfo(Model model, Principal p) {
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voter = findVoterByEmail(username);
        VoterDto voterDto = new VoterDto();
        voterDto.setFirstName(voter.getFirstName());
        voterDto.setLastName(voter.getLastName());
        voterDto.setEmail(voter.getEmail());
        voterDto.setPhone(voter.getPhone());
        voterDto.setDob(voter.getDob());
        voterDto.setGender(voter.getGender());
        voterDto.setAddress(voter.getAddress());
        voterDto.setCity(voter.getCity());
        //voterDto.setState(voter.getState());
        voterDto.setState(voter.getState().getStateName());
        voterDto.setZip(voter.getZip());
        voterDto.setCounty(voter.getCounty().getName());
        //voterDto.setCounty(voter.getCounty());
        voterDto.setParty(voter.getParty());
        //voterDto.addAttribute("voter", voterDto);
        model.addAttribute("voter", voterDto);
    }

    @Override
    public void updateVoter(VoterDto voterDto, Voter voter) {
        convertDtoToVoter(voterDto, voter);
        voterDao.save(voter);
    }

    @Override
    public Election findElectionById(Long id) {
        return electionDao.findById(id).orElseThrow(() -> new NoSuchElementException("Election with id " + id + " is not found"));
    }


    private Voter convertDtoToVoter (VoterDto voterDto, Voter voter){
            //voter.setId(voterDto.getId());
            voter.setFirstName(voterDto.getFirstName());
            voter.setLastName(voterDto.getLastName());
            voter.setEmail(voterDto.getEmail());
            voter.setDob(voterDto.getDob());
            voter.setGender(voterDto.getGender());
            voter.setSsn(passwordEncoder.encode(voterDto.getSsn()));
            voter.setIdNumber(voterDto.getIdNumber());
            voter.setAddress(voterDto.getAddress());
            voter.setCity(voterDto.getCity());
            //voter.setState(voterDto.getState());
            voter.setZip(voterDto.getZip());
           // voter.setCounty(voterDto.getCounty());
            voter.setVoted(voterDto.isVoted());
            voter.setPhone(voterDto.getPhone());
            voter.setParty(voterDto.getParty());
            return voter;
        }
    }