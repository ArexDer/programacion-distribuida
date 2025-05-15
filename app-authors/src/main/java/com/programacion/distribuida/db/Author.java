package com.programacion.distribuida.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer version;

    // Relación ManyToMany por ISBN, sin relación JPA
    @ElementCollection
    private Set<String> bookIsbns;
}