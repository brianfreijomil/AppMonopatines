package com.ScootersApp.Service;


import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.domain.User;
import com.ScootersApp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("UserService")
public class UserService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        List<User> users = this.repository.findAll();
        return users.stream().map(s1-> new UserResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO save(UserRequest user) throws Exception {
        if(!repository.existsById(user.getID())){
            User newUser= this.repository.save(new User(user.getID(), user.getName(), user.getSurname(),
                                    user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getRoles()));
            return new UserResponseDTO(newUser);
        }
        throw new Exception();
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDTO findByMailAndPassword(String mail, String pass) {
        System.out.println(pass);
        System.out.println(mail);
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDTO findMyMail(String mail) {
        User u = this.repository.findByMail(mail);
        return new UserLoginResponseDTO(u);
    }

    public UserLoginResponseDTO findByMail(String mail) {
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);
    }
}
