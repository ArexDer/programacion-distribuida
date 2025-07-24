package com.programacion.distribuida.book.rest;

import com.programacion.distribuida.book.clients.AuthorRestClient;
import com.programacion.distribuida.book.db.Book;
import com.programacion.distribuida.book.repo.BooksRepository;
import com.programacion.distribuida.book.service.dto.AuthorDto;
import com.programacion.distribuida.book.service.dto.BookDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @Inject
    ModelMapper mapper;

    @Inject
    @RestClient
    private AuthorRestClient client;

    //GET - Buscar por ISBN
    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {
        // Usar método personalizado del repositorio
        var obj = booksRepository.findByIsbnBook(isbn);

        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        BookDto ret = new BookDto();
        mapper.map(obj.get(), ret);

        try {
            var authors = client.findByIsbn(isbn)
                    .stream()
                    .map(AuthorDto::getName)
                    .toList();
            ret.setAuthors(authors);
        } catch (Exception e) {
            ret.setAuthors(Collections.emptyList());
        }

        return Response.ok(ret).build();
    }

    //GET - Listar todos los libros (SIN @Transactional)
    //GET - Listar todos los libros (Versión simplificada)
    @GET
    public List<BookDto> findAll() {
        return booksRepository.streamAll()
                .map(book -> {
                    var dto = new BookDto();
                    mapper.map(book, dto);
                    // No cargar autores en listado general para evitar bloqueos
                    dto.setAuthors(Collections.emptyList());
                    return dto;
                })
                .toList();
    }

    //POST - Crear nuevo libro
    @POST
    @Transactional
    public Response insert(Book book) {
        booksRepository.persist(book);
        return Response.status(Response.Status.CREATED).build();
    }

    //PUT - Actualizar libro existente
    @PUT
    @Path("/{isbn}")
    @Transactional
    public Response update(@PathParam("isbn") String isbn, Book book) {
        var existingBook = booksRepository.findByIdOptional(isbn);
        if (existingBook.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        book.setIsbn(isbn);
        booksRepository.getEntityManager().merge(book);
        return Response.ok().build();
    }

    //DELETE - Eliminar libro
    @DELETE
    @Path("/{isbn}")
    @Transactional
    public Response delete(@PathParam("isbn") String isbn) {
        if (!booksRepository.findByIdOptional(isbn).isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            // Eliminar dependencias en orden correcto
            booksRepository.deleteLineItemsByIsbn(isbn);
            booksRepository.deleteBookAuthorsById(isbn);
            booksRepository.deleteInventoryByIsbn(isbn);

            // Finalmente eliminar el libro
            booksRepository.deleteById(isbn);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}