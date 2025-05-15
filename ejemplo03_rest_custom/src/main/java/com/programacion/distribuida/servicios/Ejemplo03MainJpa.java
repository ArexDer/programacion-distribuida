package com.programacion.distribuida.servicios;

import com.programacion.distribuida.db.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class Ejemplo03MainJpa {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("posts-unit");
        EntityManager em = emf.createEntityManager();
        var post1= em.find(Post.class, 1);
        System.out.println(post1);
    }
}