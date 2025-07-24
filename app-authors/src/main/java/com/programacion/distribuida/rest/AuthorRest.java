package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

//URL  http://localhost:8080/api/authors
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthorRest {

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer httpPort;

    @Inject
    AuthorRepository authorRepository;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        var obj = authorRepository.findByIdOptional(id);

        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(obj.get()).build();
    }

    // http://localhost:8080/api/authors
    @GET
    public List<Author> findAll() {
        return authorRepository.listAll();
    }

    // http://localhost:8080/api/authors/find/ISB-002
    @GET
    @Path("/find/{isbn}")
    public List<Author> findByBook(@PathParam("isbn") String isbn) {
        var ret = authorRepository.findByBook(isbn);
        return ret.stream()
                .map(obj -> {
                    String newName = String.format("%s (%d)", obj.getName(), httpPort);
                    obj.setName(newName);
                    return obj;
                }).toList();
    }

    @POST
    // http://localhost:8080/api/authors
    @Transactional
    public Response create(Author author) {
        try {
            authorRepository.persist(author);
            return Response.status(Response.Status.CREATED).entity(author).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Author author) {
        var existingAuthor = authorRepository.findByIdOptional(id);

        if (existingAuthor.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            author.setId(id);
            authorRepository.getEntityManager().merge(author);
            return Response.ok(author).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        var author = authorRepository.findByIdOptional(id);

        if (author.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            authorRepository.delete(author.get());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}