package com.israr.israr_zaslavskaya_inga_voting_app.controller;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.*;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterChoiceDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import com.israr.israr_zaslavskaya_inga_voting_app.service.VoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class AuthorizationController {
    private final VoterDao voterDao;
    private final CandidateDao candidateDao;
    private final ElectionDao electionDao;
    private final VoterChoiceDao voterChoiceDao;
    private final StateDao stateDao;
    private VoteService voteService;
    private CountyDao countyDao;
   // private CustomVoterDetailsService customVoterDetailsService;
    private VoterChoice voterChoice;

    @Autowired
    public AuthorizationController(VoteService voteService, VoterDao voterDao, CandidateDao candidateDao, ElectionDao electionDao, CountyDao countyDao, VoterChoiceDao voterChoiceDao, StateDao stateDao) {
        this.voteService = voteService;
        this.voterDao = voterDao;
        this.candidateDao = candidateDao;
        this.electionDao = electionDao;
        this.countyDao = countyDao;
        this.voterChoiceDao = voterChoiceDao;
        this.stateDao = stateDao;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/main-menu")
    public String mainMenu() {
        return "main-menu";
    }

    @GetMapping("/menu-admin")
    public String adminMenu() {
        return "menu-admin";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        VoterDto voter = new VoterDto();
        model.addAttribute("voter", voter);
        return "register-voter";
    }

    @PostMapping("/register/save")
    public String registerVoter(@Valid @ModelAttribute("voter") VoterDto voter, BindingResult bindingResult, Model model) {
        voteService.createVoter(voter, bindingResult, model);
        return "redirect:/register?success";
    }

    @GetMapping("/confirmInfo")
    public String confirmInfo( Model model, Principal p) {
        voteService.displayVoterInfo(model, p);
        return "confirmInfo";
    }

    @PutMapping("/confirmInfo/save")
    public String confirmAndSaveVoterChanges( Model model, @Valid @ModelAttribute("voter") VoterDto voterDto, Principal p){
        String username = p.getName();
        Voter existing = voteService.findVoterByEmail(username);
        voterDto.setId(voterDto.getId());

        System.out.println("going to update voter " + voterDto.getId());

        voteService.updateVoter(voterDto, existing);
        log.info("Updating voter with ID={}", voterDto.getIdNumber());
        return "redirect:/confirmInfo?success";
    }

    @GetMapping("/register-candidate")
    public String registerCandidateForm(Model model) {
        CandidateDto candidate = new CandidateDto();
        model.addAttribute("candidate", candidate);
        return "register-candidate";
    }

    @PostMapping("/register-candidate/save")
    public String registerCandidate(@Valid @ModelAttribute("candidate") CandidateDto candidate, BindingResult bindingResult, Model model) {
       voteService.findOrCreateCandidate(candidate, bindingResult, model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("candidate", candidate);
            return "redirect:/register-candidate";
        }
        return "redirect:/register-candidate?success";
    }

    @GetMapping("/set-election")
    public String saveElectionForm(@ModelAttribute String position, Model model) {

        Election election = new Election();
        List<County> counties = voteService.getCounties();

        model.addAttribute("election", election);
        model.addAttribute("counties", counties);
        return "set-election";
    }

    @PostMapping("/set-election/save")
    public String saveElection(@Valid @ModelAttribute("election") ElectionDto election, BindingResult bindingResult, Model model) {
        try {
            Election existing = null;
            List<Election> elections = electionDao.findAll();
            for (Election e : elections) {
                if (e.getPosition().equals(election.getPosition()) && e.getIsActive() == election.getIsActive() && e.getYear().equals(election.getYear())) {
                    existing = e;
                    bindingResult.rejectValue("name", null, "This section of the ballot already exists");
                }
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("election", election);
                return "set-election";
            }

            if (existing == null) {
                existing = new Election();
                existing.setPosition(election.getPosition());
                existing.setIsActive(election.getIsActive());
                existing.setYear(election.getYear());
                voteService.saveELection(existing); // Save existing instead of election
            }

            return "redirect:/set-election?success";
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Optionally handle and return a specific error page
            return "redirect:/main-menu";
        }
//        Election existing = null;
//        List<Election> elections = electionDao.findByIsActive(election.getIsActive());
//        for (Election e : elections) {
//            if (e.getPosition() == election.getPosition()) {
//                existing = e;
//            }
//        }
//        if (existing != null) {
//            bindingResult.rejectValue("name", null, "This section of the ballot already exists");
//        }
//
//        voteService.saveElection(election);
//        return "redirect:/set-election?success";
    }

    @GetMapping("/election")
    public String electionForm(Model model) {
        voteService.displayELection("President", model, "election1");
        return "election1";
    }


    @PostMapping("/election/save")
    public String election1(@Valid @ModelAttribute("voterChoice") VoterChoiceDto voterChoiceDto, Principal p, Model model) {
        voteService.voteElection(voterChoiceDto, p, model);
        return "redirect:/election?success";

    }

    @GetMapping("/review")
    public String reviewForm(Model model, Principal p) {
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voter = voteService.findVoterByEmail(username);
        List<VoterChoice> selected = voteService.findVoterChoicesByVoterEmail(username);
        model.addAttribute("selected", selected);
        return "review";
    }

    @PostMapping("/review/save")
    public String submitReview(Model model, Principal p){
        String username = p.getName();
        Voter voter = voteService.findVoterByEmail(username);
        if(!voter.isVoted()){
            voter.setVoted(true);
        }
//        VoterDto voterDto = new VoterDto();
//        voterDto.setId(voter.getId());
//        voter.setFirstName(voterDto.getFirstName());
//        voterDto.setLastName(voter.getLastName());
//        voterDto.setEmail(voter.getEmail());
//        voterDto.setPhone(voter.getPhone());
//        voterDto.setDob(voter.getDob());
//        voterDto.setGender(voter.getGender());
//        voterDto.setAddress(voter.getAddress());
//        voterDto.setCity(voter.getCity());
//        //voterDto.setState(voter.getState());
//        voterDto.setZip(voter.getZip());
//        //voterDto.setCounty(voter.getCounty());
//        voterDto.setParty(voter.getParty());
////        System.out.println("going to update voter " + existing.getIdNumber());
////        System.out.println("voterId: " + voterDto.getId());
//        voteService.saveVoter(voterDto);
        System.out.println("Username: " + username+ "voted: " + voter.isVoted());
        voterDao.save(voter);
        return "submitted";
    }

    @GetMapping("/submitted")
    public String submitted(){
        return "submitted";
    }

    @GetMapping("candidateInfo")
    public String viewCandidatesInfo(Model model){
        List<Candidate> candidates = candidateDao.findAll();
        model.addAttribute("candidates", candidates);
        return "candidates-info";
    }

    @GetMapping("/election2")
    public String electionForm2(Model model) {
        voteService.displayELection("Senator", model, "election2");
        return "election2";
    }


    @PostMapping("/election2/save")
    public String election2(@Valid @ModelAttribute("voterChoice") VoterChoiceDto voterChoiceDto, Principal p, Model model) {
        voteService.voteElection(voterChoiceDto, p, model);
        return "redirect:/election2?success";

    }

    @GetMapping("/viewResults")
    public String viewResults(Model model) {
        voteService.displayElectionResults(model);
        return "viewResults"; // Thymeleaf template to display results

        //return "viewResults";
    }

    @GetMapping("/search-election")
    public String searchElection(Model model) {
        List<Election> elections = electionDao.findAll();
        model.addAttribute("elections", elections);
        return "search-election";
    }

    @GetMapping("/update-election/{id}")
    public String updateElectionForm(@PathVariable Long id, Model model) {
        Election election = voteService.findElectionById(id);
        model.addAttribute("election", election);
        return "update-election";
    }

    @PutMapping("/update-election/save/{id}")
    public String updateElection(@PathVariable Long id, @ModelAttribute("election") ElectionDto electionDto, BindingResult bindingResult, Model model){
        Election existing = voteService.findElectionById(id);
        if (bindingResult.hasErrors()) {
            // Handle validation errors if any
            model.addAttribute("election", electionDto);
            return "update-election"; // Return to the form with validation errors
        }
        if(existing == null){
            return "redirect:/update-election?error";
        }
        //existing.setId(id);
        existing.setPosition(electionDto.getPosition());
        existing.setIsActive(electionDto.getIsActive());
        existing.setYear(electionDto.getYear());
        voteService.saveELection(existing);
        return "redirect:/update-election?success";
    }

    @GetMapping("/search-candidates")
    public String viewCandidates(Model model) {
        List<Candidate> candidates = voteService.findAllCandidates();
        model.addAttribute("candidates", candidates);
        return "search-candidates";
    }

    @DeleteMapping("/{id}")
    public String deleteCandidate(@PathVariable Long id, Model model) {
        Boolean deleted = voteService.deleteCandidateById(id);
        if(deleted){
            model.addAttribute("successMessage", "Item deleted successfully.");
            log.info("Deleting candidate with ID={}", id);
        } else {
           // redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete item.");
        }

        return "redirect:/search-candidates?success";
    }

}