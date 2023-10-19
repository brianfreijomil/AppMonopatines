package com.ScootersApp.Service.DTOs.User.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String mail;
    private String password;
}
