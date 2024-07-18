package com.israr.israr_zaslavskaya_inga_voting_app.service;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.*;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VoteServiceImpl implements VoteService{
    private VoterChoiceDao voterChoiceDao;
    private VoterDao voterDao;
    private CandidateDao candidateDao;
    private RoleDao roleDao;
   // private PasswordEncoder passwordEncoder;
    private ElectionDao electionDao;


    public VoteServiceImpl(VoterDao voterDao, CandidateDao candidateDao, RoleDao roleDao,
                           ElectionDao electionDao,/* PasswordEncoder passwordEncoder, HttpSession httpSession*/ VoterChoiceDao voterChoiceDao
    ) {
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.roleDao = roleDao;
        this.electionDao = electionDao;
        //this.passwordEncoder = passwordEncoder;
       // this.httpSession = httpSession;
        this.voterChoiceDao = voterChoiceDao;

    }

    @Override
    public void saveVoter(VoterDto voterDto) {
        Voter voter = convertDtoToVoter(voterDto);

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

    private Voter convertDtoToVoter(VoterDto voterDto) {
        Voter voter = new Voter();
        voter.setId(voterDto.getId());
        voter.setFirstName(voterDto.getFirstName());
        voter.setLastName(voterDto.getLastName());
        voter.setEmail(voterDto.getEmail());
        voter.setDob(voterDto.getDob());
        voter.setGender(voterDto.getGender());
        //voter.setSsn(passwordEncoder.encode(voterDto.getSsn()));
        voter.setIdNumber(voterDto.getIdNumber());
        voter.setAddress(voterDto.getAddress());
        voter.setCity(voterDto.getCity());
        voter.setState(voterDto.getState());
        voter.setZip(voterDto.getZip());
        voter.setCounty(voterDto.getCounty());
        voter.setVoted(voterDto.isVoted());
        voter.setPhone(voterDto.getPhone());
        voter.setParty(voterDto.getParty());
        return voter;
    }
}
