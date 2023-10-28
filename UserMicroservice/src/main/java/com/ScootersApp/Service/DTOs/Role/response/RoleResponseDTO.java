package com.ScootersApp.Service.DTOs.Role.response;

import com.ScootersApp.domain.Role;
import lombok.Data;

@Data
public class RoleResponseDTO {
    private String tipo;

    public RoleResponseDTO(Role role) {
        this.tipo = role.getTipo();
    }
}
