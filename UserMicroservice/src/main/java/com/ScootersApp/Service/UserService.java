package com.ScootersApp.Service;


import com.ScootersApp.Service.DTOs.User.request.UserRequest;
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

    public void save(UserRequest user) {
        if(!repository.existsById(user.getID())){
            this.repository.save(new User(user.getID(), user.getName(), user.getSurname(),
                                    user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getRoles()));
        }

    }

    public UserLoginResponseDTO findByMailAndPassword(String mail, String pass) {
        User u = this.repository.findByMailAndPassword(mail, pass);
        return new UserLoginResponseDTO(u);
    }

    public UserLoginResponseDTO findMyMail(String mail) {
        User u = this.repository.findByMail(mail);
        return new UserLoginResponseDTO(u);
    }
}
