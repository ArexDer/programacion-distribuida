job "app-authors" {
  datacenters = ["dc1"]
  type        = "service"

  group "app-authors" {
    count = 1
    network {
      port "http" {
        static = 8080
      }
    }

    task "app-authors" {
      driver = "java"

      config {
        jar_path = "C:/Distribuida2025/app-authors/quarkus-run.jar"
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
        name     = "app-authors-http"
        port     = "http"
        tags     = ["quarkus-app"]
      }
    }
  }
}
