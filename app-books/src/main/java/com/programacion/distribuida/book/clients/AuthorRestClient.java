package com.programacion.distribuida.book.clients;

import com.programacion.distribuida.book.service.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

//URL  http://localhost:8080/api/authors
@Path("/api/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "authors.api")
/*
Me permite configurarlo aun mejor el ConfigKey
 */
//@RegisterRestClient(baseUri ="http://localhost:8080")

public interface AuthorRestClient {

    /*
    Copia del servicio original pero sin implementaciones
     */

    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByIsbn(@PathParam("isbn") String isbn);

}
