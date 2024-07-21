package com.application.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fabricante")
@Builder
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "nombre")
    private String name;

    @OneToMany(mappedBy= "maker",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval =true)
    @JsonIgnore
    private List<Product> product = new ArrayList<>();
}
