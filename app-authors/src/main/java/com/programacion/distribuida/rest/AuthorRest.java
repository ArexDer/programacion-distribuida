package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repo.AuthorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;


//URL  http://localhost:8080/api/authors
@Path("authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthorRest {

    //ESTO ES ID
    //Lo usamos cuando el componente es CDI
    @Inject //Si comento esto, ya no seria portable, RECOMENDADO PONERLA.
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer httpPort;

    @Inject
    AuthorRepository authorRepository;

    // GET por ID
    //URL  http://localhost:8080/api/authors/2
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id){
        var obj = authorRepository.findByIdOptional(id);

        if(obj.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        } else{
            return Response.ok(obj.get()).build();
        }
    }

    // GET lista completa
    //URL  http://localhost:8080/api/authors
    @GET
    public List<Author> findAll(){
        return authorRepository.listAll();
    }

    // POST (crear nuevo Author)
    //URL  http://localhost:8080/api/authors
    @POST
    @Transactional
    public Response create(Author author){
        authorRepository.persist(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    // PUT (actualizar Author existente)
    //URL  http://localhost:8080/api/authors/2
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Author updatedAuthor){
        var existingAuthor = authorRepository.findByIdOptional(id);

        if(existingAuthor.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Author author = existingAuthor.get();
        author.setName(updatedAuthor.getName());
        author.setVersion(updatedAuthor.getVersion());

        return Response.ok(author).build();
    }

    // DELETE (eliminar Author)
    //URL  http://localhost:8080/api/authors/1
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        var author = authorRepository.findByIdOptional(id);

        if(author.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        authorRepository.delete(author.get());
        return Response.noContent().build();
    }

    @GET
    @Path("/find/{isbn}")
    public List<Author> findByBookIsbn(@PathParam("isbn") String isbn){
        var ret =authorRepository.findByBook(isbn);
        /*
        Otra opcion dep uerto es usar el LOOK-UP
        Lo usamos cunado el componente no es un CDI,
        Config config = ConfigProvider.getConfig();
        var puerto = config.getOptionalValue("quarkus.http.port", Integer.class).orElse(8080);

         */
        Config config =ConfigProvider.getConfig();
        config.getConfigSources().forEach(obj -> {
            System.out.printf("%d -> %s \n", obj.getOrdinal(),obj.getName());
        });


        return ret.stream().map(obj ->{
            String newName = String.format("%s  (%d)",obj.getName(),httpPort);
            obj.setName(newName);
            return obj;

        }).toList();

    }
}
