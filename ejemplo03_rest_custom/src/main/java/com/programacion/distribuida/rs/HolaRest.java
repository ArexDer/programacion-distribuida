package com.programacion.distribuida.rs;


import com.programacion.distribuida.servicios.StringService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/hello")
@ApplicationScoped
public class HolaRest {

    // http://127.0.0.1:8080/hello?txt=hola mundo

    // http://127.0.0.1:8080/api/hello?txt=hola mundo      :::::DE LA CLASE: RestApplication


    @Inject
    StringService service;

    @GET
    public String hola(@QueryParam("txt")String txt){

        return service.convert(txt);
    }

}
