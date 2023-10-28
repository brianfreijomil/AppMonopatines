package com.scooter.apiGateway.controller;

import com.scooter.apiGateway.DTO.DisableDTO;
import com.scooter.apiGateway.DTO.UserDTO;
import com.scooter.apiGateway.DTO.UserRequestCreateDTO;
import com.scooter.apiGateway.DTO.UserRequestDTO;
import com.scooter.apiGateway.service.Constants;
import com.scooter.apiGateway.service.UsersService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @PutMapping("/{email}/disable")
    public ResponseEntity disableUser(@PathVariable String email, @RequestBody DisableDTO status){
        return this.usersService.disableUsers(email, status);
    }


}
