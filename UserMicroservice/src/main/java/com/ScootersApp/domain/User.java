package com.ScootersApp.domain;

import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public User(String name, String surname, String mail, String password, Long phoneNumber, List<Role> roles) {
    }
    public User() {

    }
}
