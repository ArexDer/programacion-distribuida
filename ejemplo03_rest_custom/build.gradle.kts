plugins {
    id("java")
    //
    //id("application")
    id("io.freefair.lombok") version "8.13.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

    //DEBE TENER EN ETE EJEMPLO EL SERVIDOR

    //API CDI
    //implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")

    //API JAX-RS
   // implementation("jakaArta.ws.rs:jakarta.ws.rs-api:4.0.0")


    //CDI
    // https://mvnrepository.com/artifact/org.jboss.weld.se/weld-se-core

     //implementation("org.jboss.weld.se:weld-se-core:6.0.2.Final")
   // implementation("org.jboss.weld.servlet:weld-se-servlet-core:6.0.2.Final")

    //motor resteasy REST: RESTEasy
    // https://mvnrepository.com/artifact/org.jboss.resteasy/resteasy-core
    implementation("org.jboss.resteasy:resteasy-core:6.2.12.Final")
    implementation("org.jboss.resteasy:resteasy-json-binding-provider:6.2.12.Final")


    //Nos falta  un servidor http, en la documentacion estEasy

    //SeBootstrap -> nos peri¿mite levantar el servidor
    implementation("org.jboss.resteasy:resteasy-undertow-cdi:6.2.12.Final")


    //FALTA LA DEPENDENCIA DE JNDER

   // implementation("io.smallrye:jandex:3.2.7")


    //JPA: EclipseLink
    // https://mvnrepository.com/artifact/org.eclipse.persistence/eclipselink
    implementation("org.eclipse.persistence:eclipselink:4.0.6")
    implementation("org.postgresql:postgresql:42.7.5")


    //Lombok



}

sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main") )
    }
}

tasks.shadowJar{
    mergeServiceFiles()


}
tasks.jar{
    manifest{
        attributes(
        "Main-Class" to "com.programacion.distribuida.Ejemplo03Main")
    }

}