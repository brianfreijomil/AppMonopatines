package com.ScootersApp.Service;


import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.domain.User;
import com.ScootersApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = this.repository.findAll();
        return users.stream().map(s1-> new UserResponseDTO(s1)).collect(Collectors.toList());
    }

    public void save(User user) {
    }

    public UserLoginResponseDTO findByMail(String mail, String pass) {
        User u = this.repository.findByMailAndPassword(mail, pass);
        return new UserLoginResponseDTO(u);
    }
}
