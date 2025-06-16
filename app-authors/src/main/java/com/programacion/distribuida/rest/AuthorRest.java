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
import java.util.concurrent.atomic.AtomicInteger;


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


    AtomicInteger index = new AtomicInteger(1);

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
        int valor = index.getAndIncrement();
        if (valor % 5 != 0) {
            String msg = String.format("Intento %d , generando error", valor);
            System.out.println("authors ****** |||| ******* " + msg);
            throw new RuntimeException(msg);
        }

        return authorRepository.listAll();
    }

    // http://localhost:8080/api/authors/find/ISB-002
    @GET
    @Path("/find/{isbn}")
    public List<Author> findByBook(@PathParam("isbn") String isbn) {

//        Config config = ConfigProvider.getConfig();
//        var puerto = config.getValue("quarkus.http.port", Integer.class);

        //generar errores
        int valor = index.getAndIncrement();
        if (valor % 5 != 0) {
            String msg = String.format("Intento %d , generando error", valor);
            System.out.println("authors ****** |||| ******* " + msg);
            throw new RuntimeException(msg);
        }

        Config config = ConfigProvider.getConfig();
        config.getConfigSources()
                .forEach(
                        obj ->
                                System.out.printf("%d -> %s\n", obj.getOrdinal(), obj.getName())

                );

        var ret = authorRepository.findByBook(isbn);
        return ret.stream()
                .map(obj -> {
                    String newName = String.format("%s (%d)", obj.getName(), httpPort);
                    obj.setName(newName);
                    return obj;
                }).toList();
    }
}