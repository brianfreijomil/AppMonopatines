package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody User user){
        this.repository.save(user);
    }

    @PostMapping("/")
    public ResponseEntity login(@RequestBody User user){
        User userRequest = this.repository.findByE_mailAndPassword(user.getE_mail(), user.getPassword());
        return new ResponseEntity(userRequest, HttpStatus.OK);
    }
}
