package com.ScootersApp.Service.DTOs.User.request;

import com.ScootersApp.domain.Role;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class UserRequest {

    private Long ID;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private Long phoneNumber;
    //private GPS Ubication;
    //private List<Account> accounts;
    private List<Role> roles;
}
