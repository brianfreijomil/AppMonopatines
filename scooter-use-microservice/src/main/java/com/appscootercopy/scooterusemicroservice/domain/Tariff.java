package com.appscootercopy.scooterusemicroservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Double cost;
    @Column
    private Boolean available;
    @Column(unique = true)
    private Long type;

    public Tariff(Double cost, Boolean available, Long type) {
        this.cost=cost;
        this.available=available;
        this.type=type;
    }
}
