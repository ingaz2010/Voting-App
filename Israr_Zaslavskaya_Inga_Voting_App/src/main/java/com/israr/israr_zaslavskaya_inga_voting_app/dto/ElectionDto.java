package com.israr.israr_zaslavskaya_inga_voting_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ElectionDto {
    private long id;
    @NotEmpty
    private String position;
    private Boolean isActive;
    private String year;
}

