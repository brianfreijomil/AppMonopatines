package com.ScootersApp.Service.DTOs.User.response;

import com.ScootersApp.domain.Role;
import com.ScootersApp.domain.User;
import lombok.Data;
import java.util.List;

@Data
public class UserLoginResponseDTO {
        private final String mail;
        private final String password;
        private final List<Role> roles;

        public UserLoginResponseDTO(User u) {
            this.mail = u.getMail();
            this.password = u.getPassword();
            this.roles = u.getRoles();
        }
}
