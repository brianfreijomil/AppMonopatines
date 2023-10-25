package com.ScootersApp.Service.DTOs.User.request;

import com.ScootersApp.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Getter
@AllArgsConstructor
public class UserRequest {
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "surname cannot be null")
    @NotEmpty(message = "surname cannot be empty")
    private String surname;
    @NotNull(message = "mail cannot be null")
    @NotEmpty(message = "mail cannot be empty")
    private String mail;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    private String password;
    @NotNull(message = "phoneNumber cannot be null")
    @NotEmpty(message = "phoneNumber cannot be empty")
    private Long phoneNumber;
    //private GPS Ubication;
    //private List<Account> accounts;
    private List<Role> roles;
}
