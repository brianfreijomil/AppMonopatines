package com.ScootersApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    private Long ID;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private Long phoneNumber;
    //private GPS Ubication;
    //private List<Account> accounts;
    @ManyToMany(mappedBy = "role")
    private List<Role> roles;
}
