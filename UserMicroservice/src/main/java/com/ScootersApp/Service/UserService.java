package com.ScootersApp.Service;


import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.Service.exceptions.ConflictExistException;
import com.ScootersApp.Service.exceptions.NotFoundException;
import com.ScootersApp.domain.User;
import com.ScootersApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity save(UserRequest user) throws Exception {
        if(this.repository.findByMail(user.getMail())==null){
            User newUser= this.repository.save(new User(user.getName(), user.getSurname(),
                                    user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getRoles()));
            return new ResponseEntity(newUser.getID(), HttpStatus.CREATED);
        }
        throw new ConflictExistException("User", "mail", user.getMail());
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDTO findByMailAndPassword(String mail, String pass) {
        System.out.println(pass);
        System.out.println(mail);
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);
    }

    public void deleteUser(Long id) {
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }
        else
            throw new NotFoundException("User","ID",id);
    }

    public ResponseEntity updateUser(UserRequest userRequest, Long id) {
        if(this.repository.existsById(id)){
            User user = this.repository.findByID(id);
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setMail(userRequest.getMail());
            user.setPassword(userRequest.getPassword());
            user.setRoles(userRequest.getRoles());

            return new ResponseEntity(user.getID(), HttpStatus.ACCEPTED);
        }
        else
            throw new NotFoundException("User","ID",id);
     }
  
    public UserLoginResponseDTO findByMail(String mail) {
        User u = this.repository.findByMail(mail);
        System.out.println(u);
        return new UserLoginResponseDTO(u);

    }
}
