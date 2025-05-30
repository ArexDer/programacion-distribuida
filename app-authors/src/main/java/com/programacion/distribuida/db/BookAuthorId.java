package com.programacion.distribuida.db;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BookAuthorId {

    @Column(name = "books_isbn")
    private String bookIsbn;

    @Column(name = "authors_id")
    private Integer authorId; //AQUI
}

