package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository {

    @Inject
    EntityManager em;

    public List<Post> findAll(){
        return  em.createQuery("select p from Post p", Post.class).
                getResultList();
    }


    public Optional<Post> findById(Integer id){
        var ret = em.find(Post.class, id);

        return Optional.ofNullable(ret);

        /*
        if(ret!=null)
            return Optional.of(ret);
        return Optional.empty();
        */


    }



}
