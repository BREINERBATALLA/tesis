package com.breiner.tesis.entity;


import com.breiner.tesis.enumeration.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private Long idUser;

    private String identificationNumber;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
