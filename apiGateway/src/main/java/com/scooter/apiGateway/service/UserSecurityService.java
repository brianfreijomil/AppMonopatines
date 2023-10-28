package com.scooter.apiGateway.service;

import com.scooter.apiGateway.DTO.UserDTO;
import com.scooter.apiGateway.DTO.UserRequestDTO;
import com.scooter.apiGateway.DTO.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private WebClient.Builder webClient;
    private static final String URL = "http://localhost:8081";

    public UserSecurityService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserResponseDTO userDTO = this.webClient.build()
                .get()
                .uri(URL + "/api/users/login/{email}", username)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .block();


        System.out.println(userDTO);
        System.out.println(grantedAuthorities(userDTO.getRoles()));
        return User.builder()
                .username(userDTO.getMail())
                .password(userDTO.getPassword())
                .authorities(this.grantedAuthorities(userDTO.getRoles()))
                .accountLocked(false)
                .disabled(false)
                .build();
    }


    private List<GrantedAuthority> grantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorities;
    }
}
