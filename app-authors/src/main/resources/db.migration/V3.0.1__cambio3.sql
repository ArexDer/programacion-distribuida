-- Tabla de libros
CREATE TABLE books (
                       isbn    VARCHAR(255) NOT NULL,
                       title   VARCHAR(128),
                       price   DECIMAL(12, 2),
                       version INTEGER,
                       CONSTRAINT pk_books PRIMARY KEY (isbn)
);

-- Tabla de inventario de libros
CREATE TABLE inventory (
                           isbn     VARCHAR(255) NOT NULL,
                           sold     INTEGER,
                           supplied INTEGER,
                           CONSTRAINT pk_inventory PRIMARY KEY (isbn)
);

-- Restricción de clave foránea: inventory.isbn → books.isbn
ALTER TABLE inventory
    ADD CONSTRAINT fk_inventory_books
        FOREIGN KEY (isbn)
            REFERENCES books (isbn);

-- Tabla de relación muchos-a-muchos entre libros y autores
CREATE TABLE books_authors (
                               books_isbn VARCHAR(255) NOT NULL,
                               authors_id INTEGER NOT NULL,
                               CONSTRAINT pk_books_authors PRIMARY KEY (books_isbn, authors_id)
);

-- Restricción de clave foránea: books_authors.authors_id → authors.id
ALTER TABLE books_authors
    ADD CONSTRAINT fk_books_authors_authors
        FOREIGN KEY (authors_id)
            REFERENCES authors (id);

-- Restricción de clave foránea: books_authors.books_isbn → books.isbn
ALTER TABLE books_authors
    ADD CONSTRAINT fk_books_authors_books
        FOREIGN KEY (books_isbn)
            REFERENCES books (isbn);
