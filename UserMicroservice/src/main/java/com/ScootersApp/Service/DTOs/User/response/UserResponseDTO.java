package com.ScootersApp.Service.DTOs.User.response;

import com.ScootersApp.domain.Role;
import com.ScootersApp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
public class UserResponseDTO {
    private final Long ID;
    private final String name;
    private final String surname;
    private final String mail;
    private final Long phoneNumber;
    //private GPS Ubication;
    //private List<Account> accounts;
    private final List<Role> roles;

    public UserResponseDTO(User s1) {
        this.ID = s1.getID();
        this.name = s1.getName();
        this.surname = s1.getSurname();
        this.mail = s1.getMail();
        this.phoneNumber = s1.getPhoneNumber();
        this.roles = s1.getRoles();
    }
}
