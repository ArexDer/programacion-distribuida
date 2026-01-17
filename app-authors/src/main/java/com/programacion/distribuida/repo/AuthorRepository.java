package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AuthorRepository implements PanacheRepositoryBase<Author, Integer> //se usa Panache Repository cuando el id es long
        // y RepositoryBase cpara especificar e  tipo de dato del id
{

}
