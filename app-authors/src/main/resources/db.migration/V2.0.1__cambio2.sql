-- Crear la tabla de relación entre libros y autores
CREATE TABLE books_authors (
                               books_isbn VARCHAR(255) NOT NULL,
                               authors_id INTEGER NOT NULL,
                               CONSTRAINT pk_books_authors PRIMARY KEY (books_isbn, authors_id)
);

-- Agregar la restricción de clave foránea para authors_id
ALTER TABLE books_authors
    ADD CONSTRAINT fk_books_authors_authors
        FOREIGN KEY (authors_id)
            REFERENCES authors(id);
