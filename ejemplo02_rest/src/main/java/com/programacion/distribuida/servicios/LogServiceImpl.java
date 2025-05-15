package com.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LogServiceImpl implements LogService {

    @Inject
    LogService log;

//    @PostConstruct
//    public void init() {
//        log = CDI.current().select(LogService.class).get();
//    }

    @Override
    public void print(String message) {
        System.out.println("LOG "+ message);
    }
}
