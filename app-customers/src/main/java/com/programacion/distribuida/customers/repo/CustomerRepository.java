package com.programacion.distribuida.customers.repo;

import com.programacion.distribuida.customers.db.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class CustomerRepository implements PanacheRepositoryBase<Customer, Integer> {

    public Optional<Customer> findByIdCustom(Integer id) {
        return find("id", id).firstResultOptional();
    }
}