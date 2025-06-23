job "app-books" {
  datacenters = ["dc1"]
  type        = "service"

  group "app-books" {
    count = 1

    network {
      port "http" {}
    }

    task "app-books" {
      driver = "exec"

      config {
        command = "java"
        args    = ["-jar", "C:/Distribuida2025/app-books/quarkus-run.jar"]
      }

      env {
        QUARKUS_HTTP_PORT = "${NOMAD_PORT_http}"
      }

      resources {
        cpu    = 2000  # 2000 MHz (aprox. 2 vCPU)
        memory = 1024  # en MB, 1 GB
      }

      service {
        provider = "nomad"
        name     = "app-books-http"
        port     = "http"
        tags     = ["quarkus-app"]
      }
    }
  }
}
