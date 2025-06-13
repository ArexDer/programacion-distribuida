job "app-customers" {
  datacenters = ["dc1"]
  type        = "service"

  group "app-customers" {
    count = 1
    network {
      port "http" {

      }
    }

    task "app-customers" {
      driver = "java"

      config {
        jar_path = "C:/Distribuida2025/app-customers/quarkus-run.jar"
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
        name     = "app-customers-http"
        port     = "http"
        tags     = ["quarkus-app"]
      }
    }
  }
}
