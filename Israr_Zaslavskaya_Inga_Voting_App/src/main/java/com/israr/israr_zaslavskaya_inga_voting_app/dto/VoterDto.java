package com.israr.israr_zaslavskaya_inga_voting_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoterDto {
    private long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;
    private String phone;

    @NotEmpty
    private String ssn;

    @NotEmpty
    private String idNumber;

    @NotEmpty
    private String dob;
    private String gender;
    private String address;
    private String city;

    //@NotEmpty
    private String state;
    private String zip;

    // @NotEmpty
    private String county;
    private String party;

    private boolean voted = false;

    public boolean isAdminRegistration(){
        return email.endsWith("@admin.com");
    }
}

