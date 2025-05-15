package com.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StringServiceImpl implements StringService {

    @Inject
    private LogService log;

    @Override
    public String convert(String txt) {
        log.print(" convertidor: "+txt);
        return txt.toUpperCase();
    }
}
