package com.ScootersApp.domain;

import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false)
    private Long ID;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Role> roles;
    @Column(columnDefinition = "boolean default true")
    private Boolean available;

    public User(UserRequest newUser){
        this.name = newUser.getName();
        this.surname = newUser.getSurname();
        this.mail = newUser.getMail();
        this.password = newUser.getPassword();
        this.phoneNumber = newUser.getPhoneNumber();
        this.roles = new ArrayList<>();
    }

    public User(String name, String surname, String mail, String password, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
    }
}
