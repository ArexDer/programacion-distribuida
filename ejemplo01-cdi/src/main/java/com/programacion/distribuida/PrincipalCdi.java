package com.programacion.distribuida;

import com.programacion.distribuida.servicios.StringService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;


public class PrincipalCdi {

    public static void main(String[] args)  {
        SeContainer container= null;
        try {
             container = SeContainerInitializer.newInstance().initialize();
            StringService service = container.select(StringService.class).get(); //look up

            String ret = service.convert("hola mundo");
            System.out.println(ret);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(container!=null)
                container.close();
        }
//        try (SeContainer container = SeContainerInitializer.newInstance().initialize()){
//
//            StringService service = container.select(StringService.class).get(); //look up
//
//            String ret = service.convert("hola mundo");
//            System.out.println(ret);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }



//    public static void main(String[] args) {
//        //Nos permite trabajar con @Scope
//        /*
//        Pero como no tenemos un servidor, hay que levanta r de forma manual
//
//tiene un BINS.XML .. en el quarks es opcional en AC
//         */
//        try (SeContainer container = SeContainerInitializer.newInstance().initialize()){
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//            new PrincipalCdi().run();
//
//    }

}
