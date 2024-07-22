//package com.israr.israr_zaslavskaya_inga_voting_app.controller;
//
//import com.israr.israr_zaslavskaya_inga_voting_app.dao.CandidateDao;
//import com.israr.israr_zaslavskaya_inga_voting_app.dao.ElectionDao;
//import com.israr.israr_zaslavskaya_inga_voting_app.dao.StateDao;
//import com.israr.israr_zaslavskaya_inga_voting_app.dto.CandidateDto;
//import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
//import com.israr.israr_zaslavskaya_inga_voting_app.model.Election;
//import com.israr.israr_zaslavskaya_inga_voting_app.model.State;
//import com.israr.israr_zaslavskaya_inga_voting_app.service.VoteService;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.Year;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class AdminUploadController {
//
//    @Autowired
//    private AdminUploadService adminUploadService;
//
//    @Autowired
//    private VoteService voteService;
//
//    @Autowired
//    private ElectionDao electionDao;
//    @Autowired
//    private CandidateDao candidateDao;
//    @Autowired
//    private StateDao stateDao;
//
//    @GetMapping("/register-candidate-image")
//    public String registerCandidateForm(Model model) {
//        CandidateDto candidate = new CandidateDto();
//        model.addAttribute("candidate", candidate);
//
//        List<State> states = stateDao.findAll();
//        model.addAttribute("states", states);
//
//        model.addAttribute("image", image);
//        System.out.println(states);
//        return "register-candidate-image";
//    }
//
//    @PostMapping(value = "/register-candidate-image/save", consumes = "multipart/form-data")
//    public String registerCandidate(@Valid @ModelAttribute("candidate") CandidateDto candidate,
//       @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) throws IOException {
//        //voteService.findOrCreateCandidate(candidate, bindingResult, model);
//        Candidate existing = voteService.findCandidateByName(candidate.getName());  //searching for candidate among existing
//        if (existing != null) {
//            bindingResult.rejectValue("name", null, "Candidate with this Name already exists");
//        } else {
//            //create new candidate if not found (== null)
//            existing = new Candidate();
//            existing.setName(candidate.getName());
//            existing.setRole(candidate.getRole());
//            existing.setParty(candidate.getParty());
//            existing.setDescription(candidate.getDescription());
//
//            candidateDao.save(existing);
//
//            //picture upload
//        try {
//            AdminUpload userUpload = new AdminUpload();
//            String fileName = file.getOriginalFilename();
//            userUpload.setUserProfilePic(fileName);
//            userUpload.setContent(file.getBytes());
//            userUpload.setSize(file.getSize());
//            Path path = Paths.get("uploads/" + fileName);
////            byte[] bytes = file.getBytes();
//            Files.write(path, file.getBytes());
//            String imageUrl = "uploads/" + fileName;
//            userUpload.setImageUrl(imageUrl);
////            existing.setImageUrl(imageUrl);
//           // existing.setAdminUpload(userUpload);
//            userUpload.setCandidate(existing);
//    //        System.out.println("Image id:" + existing.getAdminUpload().getId());
////            System.out.println("Image url: " + existing.getImageUrl());
//            adminUploadService.createUser(userUpload);
//    //        existing.setAdminUpload(userUpload);
//            candidateDao.save(existing);
//            model.addAttribute("success", "File Uploaded Successfully!!!");
//        } catch (IOException e) {
//            model.addAttribute("error", "Failed to upload file");
//        }
//
//        }
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("candidate", candidate);
//            return "register-candidate-image";
//        }
//
//        // Prepare statesRepresent list
//        List<State> selectedStates = new ArrayList<>();
//        if (candidate.getStates() != null) {
//            for (State state : candidate.getStates()) {
//               // State state = stateDao.findById(stateId).orElse(null);
//                if (state != null) {
//                    selectedStates.add(state);
//                }
//            }
//        }
//        candidate.setStates(selectedStates);
//        for (State state : selectedStates) {
//            state.getCandidates().add(existing);
//            stateDao.save(state);
//        }
//
//
//        String electionName = candidate.getRole();
//        System.out.println("Election " + electionName);
//        Election election = voteService.findActiveElectionByPosition(electionName);
//        //System.out.println("Adding candidate to election " + election);
//        if (election == null) {
//            System.out.println("Election " + electionName + " not found");
//            election = new Election();
//            election.setPosition(electionName);
//            election.setYear(Year.now().toString());
//            election.setIsActive(true);
//            List<Candidate> candidates = new ArrayList<>();
//            candidates.add(existing);
//            election.setCandidates(candidates);
//            System.out.println(election.getCandidates());
//            electionDao.save(election);
//            voteService.saveELection(election);
//            existing.setElection(election);
//            candidateDao.save(existing);
//        } else {
//            existing.setElection(election);
//            System.out.println("Adding candidate to election " + election);
//            existing.setElection(election);
//            candidateDao.save(existing);
//            System.out.println(election.getCandidates());
//            List<Candidate> candidates = election.getCandidates();
//            //existing.setElection(election);
//            candidates.add(existing);
//            System.out.println(candidates + " edited");
//            //election.getCandidates().add(existing);
////                election.setCandidates(candidates);
////                saveELection(election);
////                existing.setElection(election);
////                candidateDao.save(existing);
//            election.setCandidates(candidates);
//            electionDao.save(election);
//        }
//
//        return "redirect:/register-candidate-image?success";
//    }
//
//    @GetMapping("/image")
//    public void showImage(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
//        Optional<AdminUpload> userUpDownLoadOptional = adminUploadService.getAdminUploadByUd(id);
//        if (userUpDownLoadOptional.isPresent()) {
//            AdminUpload userUpDownLoad = userUpDownLoadOptional.get();
//            response.setContentType("image/jpeg"); // Adjust content type as per your image type
//            response.getOutputStream().write(userUpDownLoad.getContent());
//            response.getOutputStream().close();
//        } else {
//            // Handle case where image data is not found
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//
//    @GetMapping("candidateInfo-image")
//    public String viewCandidatesInfo(Model model){
//        List<Candidate> candidates = candidateDao.findAll();
//        model.addAttribute("candidates", candidates);
//        return "candidates-info-image";
//    }
//
//}
