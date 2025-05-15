plugins {
    id("java")
  //  id("application")
    id("war")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //API CDI
  compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")

    //API JAX-RS
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")

    //compileOnly solo cuando lo ejecuto y asi no tenog problemas con las libs de el servidor.
}

tasks.test {
    useJUnitPlatform()
}