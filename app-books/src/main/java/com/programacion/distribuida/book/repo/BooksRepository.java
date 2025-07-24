package com.programacion.distribuida.book.repo;

import com.programacion.distribuida.book.db.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class BooksRepository implements PanacheRepositoryBase<Book, String> {

    public Optional<Book> findByIsbnBook(String isbn) {
        return find("isbn", isbn).firstResultOptional();
    }

    @Transactional
    public void deleteInventoryByIsbn(String isbn) {
        getEntityManager().createNativeQuery("DELETE FROM inventory WHERE isbn = ?1")
                .setParameter(1, isbn)
                .executeUpdate();
    }

    @Transactional
    public void deleteBookAuthorsById(String isbn) {
        getEntityManager().createNativeQuery("DELETE FROM books_authors WHERE books_isbn = ?1")
                .setParameter(1, isbn)
                .executeUpdate();
    }

    @Transactional
    public void deleteLineItemsByIsbn(String isbn) {
        getEntityManager().createNativeQuery("DELETE FROM line_items WHERE isbn = ?1")
                .setParameter(1, isbn)
                .executeUpdate();
    }
}