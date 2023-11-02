package com.ScootersApp.config;


import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;


@Service
public class UserSecurityService implements UserDetailsService {

    private UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.ScootersApp.domain.User u = this.userRepository.findByMail(username);

        if(u == null) return null;

        UserLoginResponseDTO userDTO = new UserLoginResponseDTO(u);

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
