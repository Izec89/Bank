package com.izec.bank.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "usr")
public class User { //TODO implements UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private boolean active;
    private String email;
    private String activationCode;

    @Column(unique = true)
    private String phoneNumber;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User(String firstname, String lastname, boolean active, String email, String activationCode, String phoneNumber, Set<Role> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.active = active;
        this.email = email;
        this.activationCode = activationCode;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

}
