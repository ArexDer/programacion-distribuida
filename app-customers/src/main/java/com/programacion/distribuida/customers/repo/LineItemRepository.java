package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.LineItem;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class LineItemRepository implements PanacheRepositoryBase<LineItem, Integer> {

    public Optional<LineItem> findByIdCustom(Integer id) {
        return find("id", id).firstResultOptional();
    }
}