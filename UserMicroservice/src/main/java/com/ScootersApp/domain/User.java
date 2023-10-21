package com.ScootersApp.domain;

import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
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
    @ManyToMany
    private List<Role> roles;
}
