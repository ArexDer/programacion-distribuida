package com.programacion.distribuida.book.clients;

import com.programacion.distribuida.book.service.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

//URL  http://localhost:8080/api/authors
@Path("/api/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//Antes
//@RegisterRestClient(configKey = "authors.api")

//Despues
@RegisterRestClient(baseUri = "stork://authors-api")
/*
Me permite configurarlo aun mejor el ConfigKey
 */
//@RegisterRestClient(baseUri ="http://localhost:8080")

public interface AuthorRestClient {


    //http://localhost:8080/api/authors/find/{isbn}
    @GET
    @Path("/find/{isbn}")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(fallbackMethod = "findByIsbnFallback")
    public List<AuthorDto> findByIsbn(@PathParam("isbn") String isbn);

    default List<AuthorDto> findByIsbnFallback(String isbn) {

        var aut = new AuthorDto();
        aut.setId(0);
        aut.setName("---NO DISPONIBLE °°°");
        return List.of(aut);


        //return List.of();
    }

}
