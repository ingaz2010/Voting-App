package com.israr.israr_zaslavskaya_inga_voting_app.service;

import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import com.israr.israr_zaslavskaya_inga_voting_app.model.Voter;
import com.israr.israr_zaslavskaya_inga_voting_app.model.VoterChoice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VoteService {

    void saveVoter(VoterDto voterDto);

    void saveCandidate(CandidateDto candidateDto);

    //void saveVoter(VoterDto voterDto);
    //void saveCandidate(CandidateDto candidateDto);
    Voter findVoterByIdNumber(String idNumber);

    Voter findVoterByEmail(String email);

    Candidate findCandidateByName(String name);

    List<Candidate> findCandidatesByRole(String candidateRole);

    // void saveElection(ElectionDto election);

    void saveElection(ElectionDto electiondto);

    void vote(Long id, HttpSession session);

    Candidate getCandidateByName(String name);

    Voter findVoterById(Long voterId);

    void saveVoterChoice(VoterChoice voterChoice);

    List<VoterChoice> findVoterChoicesByVoterEmail(String username);

    List<VoterChoice> findAllVoterChoices();

    Long getVotesByCandidate(String name);
}
