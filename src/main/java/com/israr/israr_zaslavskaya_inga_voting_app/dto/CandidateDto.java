package com.israr.israr_zaslavskaya_inga_voting_app.dto;

import com.israr.israr_zaslavskaya_inga_voting_app.model.State;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String role;

    @NotEmpty
    private String party;
    private String description;

    private MultipartFile image;

    private List<State> states;
}

