package com.ScootersApp.controller;

import com.ScootersApp.Service.DTOs.Role.request.RoleRequest;
import com.ScootersApp.Service.DTOs.User.request.UserRequest;
import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.Service.DTOs.userAccount.request.UserAccountRequestDTO;
import com.ScootersApp.Service.DTOs.userAccount.response.UserAccountResponseDTO;
import com.ScootersApp.Service.UserService;
import com.ScootersApp.domain.DisableDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<UserResponseDTO> getAllUsers(){
        return this.service.findAll();
    }

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody @Valid UserRequest user){
        System.out.println(user);
        return  this.service.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        this.service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUser(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id){
        return this.service.updateUser(userRequest, id);
    }

   // @PostMapping("/login")
   //public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequest user){
        //UserLoginResponseDTO userRequest = this.service.findByMailAndPassword(user.getMail(), user.getPassword());
    //}

    @GetMapping("/login/{email}")
    public ResponseEntity<UserLoginResponseDTO> login(@PathVariable String email){
        return this.service.findByMail(email);
    }

    /*@GetMapping("/{mail}")
    public UserLoginResponseDTO getByMail(@PathVariable String mail){
        return this.service.findByMail(mail);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByID(@PathVariable Long id){
        return this.service.findByID(id);
    }

    @PostMapping("/{id}/account/{idAccount}")
    public ResponseEntity<UserAccountResponseDTO> saveNewUserAccount(@PathVariable Long id, @PathVariable Long idAccount){
        return this.service.saveNewUserAccount(id, idAccount);
    }
    @GetMapping("/{id}/account")
    public List<UserAccountResponseDTO> getUserAccountByUserId(@PathVariable Long id){
        return this.service.getUserAccountByUserId(id);
    }

    @PutMapping("/{mail}/disable")
    public ResponseEntity<String> disableUser(@PathVariable String mail, @RequestBody Boolean status){
        System.out.println(status);
        return this.service.disableUser(mail, status);
    }
}
