package com.programacion.distribuida.rs;

import com.programacion.distribuida.db.Post;
import com.programacion.distribuida.repo.PostRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostsRest {

  //  http://127.0.0.1:8080/api/posts

    @Inject
    private PostRepository postRepository;

    @GET
    public List<Post> findAll() {
        return postRepository.findAll();

    }

    @GET
    @Path("/{postId}")
    public Post findById(@PathParam("postId") Integer id){
            return postRepository.findById(id).orElseThrow();
        }

}

