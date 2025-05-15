package com.programacion.distribuida.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//Para que lo gestione el contenedor grande CDI
@ApplicationScoped
public class JpaConfig {

    //productor de cdi
    private EntityManagerFactory emf;
    @PostConstruct
    void init(){
        emf= Persistence.createEntityManagerFactory("posts-unit");
    }


    @Produces
    public EntityManager entityManager(){
        return emf.createEntityManager();
    }

    //AHORA VAMOS AL REPORT  PostRepository
}
