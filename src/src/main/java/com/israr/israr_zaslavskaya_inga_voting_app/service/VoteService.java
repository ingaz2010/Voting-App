package com.israr.israr_zaslavskaya_inga_voting_app.service;

import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterChoiceDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Component
public interface VoteService {

    void saveVoter(VoterDto voterDto);

    //void saveCandidate(CandidateDto candidateDto);

    //void saveVoter(VoterDto voterDto);
    //void saveCandidate(CandidateDto candidateDto);
    //Voter findVoterByIdNumber(String idNumber);

    Voter findVoterByEmail(String email);

    Candidate findCandidateByName(String name);

    //List<Candidate> findCandidatesByRole(String candidateRole);

    // void saveElection(ElectionDto election);

    void saveElection(ElectionDto electiondto);

    void vote(Long id, HttpSession session);

    //Candidate getCandidateByName(String name);

   // Voter findVoterById(Long voterId);

    //void saveVoterChoice(VoterChoice voterChoice);

    List<VoterChoice> findVoterChoicesByVoterEmail(String username);

    List<VoterChoice> findAllVoterChoices();

   // Long getVotesByCandidate(String name);

    Election findActiveElectionByPosition(String electionName);

    void saveELection(Election election);

    //Candidate findOrCandidate(CandidateDto candidateDto);

    void findOrCreateCandidate(CandidateDto candidate, MultipartFile imageFile, BindingResult bindingResult, Model model) throws IOException;

    List<County> getCounties();

    void createVoter(VoterDto voter, BindingResult bindingResult, Model model);

    void displayVoterInfo(Model model, Principal p);

    void updateVoter(VoterDto voterDto, Voter voter);

    Election findElectionById(Long id);

    String displayELection(String string, Model model, String toReturn);

    void voteElection(VoterChoiceDto voterChoiceDto, Principal p, Model model);

    void displayElectionResults(Model model);

    List<Candidate> findAllCandidates();

    Boolean deleteCandidateById(Long id);

    //void findOrCandidate(CandidateDto candidateDto);
}
