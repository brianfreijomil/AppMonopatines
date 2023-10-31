package com.ScootersApp.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id")
    User user;

    public Role(String tipo) {
        this.tipo = tipo;
    }
}
