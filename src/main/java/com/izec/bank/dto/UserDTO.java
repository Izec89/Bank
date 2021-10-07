package com.izec.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.izec.bank.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    private boolean active;
    private String email;
    private String activationCode;
    private String phoneNumber;
    private Set<Role> roles;

    public UserDTO(String firstname, String lastname, boolean active, String email, String activationCode, String phoneNumber, Set<Role> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.active = active;
        this.email = email;
        this.activationCode = activationCode;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
