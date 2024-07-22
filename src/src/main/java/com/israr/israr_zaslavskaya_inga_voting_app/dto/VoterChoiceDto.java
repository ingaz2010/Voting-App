package com.israr.israr_zaslavskaya_inga_voting_app.dto;

import com.israr.israr_zaslavskaya_inga_voting_app.model.Candidate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoterChoiceDto {
    private Long id;
@NotNull
    private Long candidateOfChoice;

    private Candidate candidateSelected;
}
