CREATE TABLE public.book_author
(
    book_id integer not null,
    author_id integer not null,

    CONSTRAINT fk_author FOREIGN KEY (author_id)
        REFERENCES public.authors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
