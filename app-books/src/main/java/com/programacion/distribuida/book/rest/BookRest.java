package com.programacion.distribuida.book.rest;


import com.programacion.distribuida.book.db.Book;
import com.programacion.distribuida.book.repo.BooksRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@ApplicationScoped
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn){
        return
                booksRepository.findByIdOptional(isbn)
                        .map(Response::ok)
                        .orElse(Response.status(Response.Status.NOT_FOUND))
                        .build();
    }

    @GET
    public List<Book> findAll(){
        return booksRepository.listAll();
    }
}
