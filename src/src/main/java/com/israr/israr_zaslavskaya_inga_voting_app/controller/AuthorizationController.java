package com.israr.israr_zaslavskaya_inga_voting_app.controller;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.*;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.ElectionDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterChoiceDto;
import com.israr.israr_zaslavskaya_inga_voting_app.dto.VoterDto;
import com.israr.israr_zaslavskaya_inga_voting_app.model.*;
import com.israr.israr_zaslavskaya_inga_voting_app.service.VoteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;
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
/* Register a voter form*/
    @GetMapping("/register")
    public String registerForm(Model model) {
        VoterDto voter = new VoterDto();
        model.addAttribute("voter", voter);
        return "register-voter";
    }

    /*Takes input and saves as a new Voter entity */
    @PostMapping("/register/save")
    public String registerVoter(@Valid @ModelAttribute("voter") VoterDto voter, BindingResult bindingResult, Model model) {
        voteService.createVoter(voter, bindingResult, model);
        return "redirect:/register?success";
    }

    /* Displays from with existing information, ready to take any updates */
    @GetMapping("/confirmInfo")
    public String confirmInfo( Model model, Principal p) {
        voteService.displayVoterInfo(model, p);
        return "confirmInfo";
    }

    /* Updates Voter's information if there are any changes. There is one more button available
    * to skip changes and move on to the voting page.
    * If user decides to change any information, State ID number and SSN need to be retyped to confirm identity */
    @PostMapping("/confirmInfo/save")
    public String confirmAndSaveVoterChanges( Model model, @Valid @ModelAttribute("voter") VoterDto voterDto, Principal p){
        String username = p.getName();

        Voter existing = voteService.findVoterByEmail(username);
        System.out.println("Updating " +existing);
        //voterDto.setId(voterDto.getId());

        System.out.println("going to update voter " + voterDto.getId());

        voteService.updateVoter(voterDto, existing);
        log.info("Updating voter with ID={}", voterDto.getIdNumber());
        return "redirect:/confirmInfo?success";
    }

    /* Create new Candidate form */
    @GetMapping("/register-candidate-image")
    public String registerCandidateForm(Model model) {
        CandidateDto candidate = new CandidateDto();
        List<State> states = stateDao.findAll();
        model.addAttribute("candidate", candidate);
        model.addAttribute("states", states);
        return "register-candidate-image";
    }

    /* saves new candidate, using position they are running for, it looks ofr existing election to be associated with,
    * if election is not found, creates the new one and adds candidate to it*/
    @PostMapping("/register-candidate-image/save")
    public String registerCandidate(@Valid @ModelAttribute("candidate") CandidateDto candidate, @RequestParam("imageFile") MultipartFile imageFile, BindingResult bindingResult, Model model) throws IOException {
       voteService.findOrCreateCandidate(candidate, imageFile, bindingResult, model);
        if (bindingResult.hasErrors()) {
            model.addAttribute("candidate", candidate);
            return "redirect:/register-candidate-image";
        }
        return "redirect:/register-candidate-image?success";
    }

    /* create new election form */
    @GetMapping("/set-election")
    public String saveElectionForm(@ModelAttribute String position, Model model) {

        Election election = new Election();
        List<County> counties = voteService.getCounties();

        model.addAttribute("election", election);
        model.addAttribute("counties", counties);
        return "set-election";
    }

    /* based on provided information creates new election */
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
    }

    /*Election page. Displays Candidates associated with the position, allows to pick one.
    * Additionally, candidates names are the links to the page with all candidates displayed with their information */
    @GetMapping("/election")
    public String electionForm(Model model) {
        voteService.displayELection("President", model, "election1");
        return "election1";
    }

/* Saves the choice in separate table, uses voter id, candidate id, election id and current date */
    @PostMapping("/election/save")
    public String election1(@Valid @ModelAttribute("voterChoice") VoterChoiceDto voterChoiceDto, Principal p, Model model) {
        voteService.voteElection(voterChoiceDto, p, model);
        return "redirect:/election?success";

    }
/* Displays voter's choices and allows voter to go back and edit their choices.
* If no correction needed,  submit. Once submitted, user's status will be changed to status = voted*/
    @GetMapping("/review")
    public String reviewForm(Model model, Principal p) {
        String username = p.getName();
        System.out.println("Username: " + username);
        Voter voter = voteService.findVoterByEmail(username);
        List<VoterChoice> selected = voteService.findVoterChoicesByVoterEmail(username);
        model.addAttribute("selected", selected);
        System.out.println("Voter status: " + voter.isVoted());
        return "review";
    }

    @Transactional
    @PostMapping("/review/save")
    public String submitReview(Model model, Principal p){
        String username = p.getName();
        Voter voter = voteService.findVoterByEmail(username);
        System.out.println("Voter submitted ballot: " + voter.isVoted());
        if(!voter.isVoted()){
            voter.setVoted(true);
        }
        System.out.println("Voter submitted ballot: " + voter.isVoted());
        System.out.println("Username: " + username+ "voted: " + voter.isVoted());
        voterDao.save(voter);
        return "redirect:/submitted";
    }

    /*COnfirms that choices have been submitted and saved */
    @GetMapping("/submitted")
    public String submitted(){
        return "submitted";
    }
/* page with information about candidates */
    @GetMapping("candidates-info-image")
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
/* view results page. Available only to admins*/
    @GetMapping("/viewResults")
    public String viewResults(Model model) {
        voteService.displayElectionResults(model);
        return "viewResults"; // Thymeleaf template to display results

        //return "viewResults";
    }

    /* Displays all elections available to be updated (like change status from active to not active */
    @GetMapping("/search-election")
    public String searchElection(Model model) {
        List<Election> elections = electionDao.findAll();
        model.addAttribute("elections", elections);
        return "search-election";
    }

    /*Once election to update is chosen, the button redirects to the form that allows to change information */
    @GetMapping("/update-election/{id}")
    public String updateElectionForm(@PathVariable Long id, Model model) {
        Election election = voteService.findElectionById(id);
        model.addAttribute("election", election);
        return "update-election";
    }

    /* Saves changes */
    @PostMapping("/update-election/save/{id}")
    public String updateElection(@PathVariable Long id, @ModelAttribute("election") ElectionDto electionDto, BindingResult bindingResult, Model model){
        Election existing = voteService.findElectionById(id);
        if (bindingResult.hasErrors()) {
            // Handle validation errors if any
            model.addAttribute("election", electionDto);
            return "update-election"; // Return to the form with validation errors
        }
        if(existing == null){
            return "redirect:/update-election/{id}?error";
        }
        //existing.setId(id);
        existing.setPosition(electionDto.getPosition());
        existing.setIsActive(electionDto.getIsActive());
        existing.setYear(electionDto.getYear());
        voteService.saveELection(existing);
        return "redirect:/update-election/{id}";
    }

    @GetMapping("/search-candidates")
    public String viewCandidates(Model model) {
        List<Candidate> candidates = voteService.findAllCandidates();
        model.addAttribute("candidates", candidates);
        return "search-candidates";
    }

//    @GetMapping("search-candidates/{id}")
//    public String viewCandidateToDelete(@PathVariable Long id, Model model) {
//        Candidate candidate = candidateDao.findById(id).orElse(null);
//        if (candidate != null) {
//            model.addAttribute("candidate", candidate);
//        }
//        return "delete";
//    }

    @PostMapping("/search-candidates/{id}")
    public String deleteCandidate(@PathVariable Long id, Model model) {
        voteService.deleteCandidateById(id);
        Boolean deleted = voteService.deleteCandidateById(id);
        if(deleted){
            model.addAttribute("successMessage", "Item deleted successfully.");
            log.info("Deleting candidate with ID={}", id);
        } else {
           // redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete item.");
        }

        return "redirect:/search-candidates";
    }
//@DeleteMapping("/{id}")
//public String deleteCandidate(@PathVariable Long id) {
//    candidateDao.deleteById(id);
//    return "Candidate deleted successfully";
//}

}