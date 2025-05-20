package com.programacion.distribuida.book.rest;


import com.programacion.distribuida.book.db.Book;
import com.programacion.distribuida.book.repo.BooksRepository;
import com.programacion.distribuida.book.service.dto.AuthorDto;
import com.programacion.distribuida.book.service.dto.BookDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Path("/books")
@ApplicationScoped
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {

        // 1.- Buscar el libro
        var obj = booksRepository.findByIdOptional(isbn);

        if (obj.isEmpty()) {
            return Response.status(404).build(); // Si no se encuentra el libro, devolver 404
        }

        // Obtener el libro desde el Optional
        var book = obj.get();

        // Crear un DTO para el libro
        BookDto ret = new BookDto();
        ret.setIsbn(isbn);
        ret.setTitle(book.getTitle());
        ret.setPrice(book.getPrice());

        //2.- Buscar el inventario
        var inventory = book.getInventory();
        if (inventory != null) {
            ret.setInventorySold(inventory.getSold());
            ret.setSupplier(inventory.getSupplied());
        }

        // Buscar los autores (puedes agregar aquí el código para los autores si es necesario)
        // ret.setAuthors(book.getAuthors());

        //3.-  Devolver la respuesta con el DTO del book
        var client = ClientBuilder.newClient();
        AuthorDto[] authors = client.target("http://localhost:8080")
                .path("/api/authors/find/{isbn}")
                .resolveTemplate("isbn", isbn)
                .request(MediaType.APPLICATION_JSON)
                .get(AuthorDto[].class);

    //SACAR LA INFORMACION DE AAUI DE MICROPROFILE
        /*
        Metodo buscar todos,
        buscar todos, iterar de uno en uno
        recupuerar los autoresy ponerlos alli
        sino que nos salga varios
        libro uno...
        libro 2 ---
        etc
         */


       // System.out.print(client);
        ret.setAuthors(
                Stream.of(authors)
                .map(AuthorDto::getName)
                        .toList()
        );


        return Response.ok(ret).build();
    }

    /*
    JAX-RS para la coneccion de servicios REST
     */



    @GET
    public List<Book> findAll(){
        return booksRepository.listAll();
    }
}
