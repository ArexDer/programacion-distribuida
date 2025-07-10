package com.programacion.distribuida.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Liveness
// http://localhost:7070/q/health/live
public class AuthorsHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("app-authores-health")
                .withData("app-authores-health", "UP ->Esta ok")
                .up()
                .build();
    }
}


/*
revisar estae URL:

INTERFZ  DE LA SALUD: http://localhost:7070/q/health-ui esta revisar no sales
Health : http://localhost:7070/q/health    OK
VIVO : http://localhost:7070/q/health/live   OK
CORRIENDO: http://localhost:7070/q/health/ready   OK
 */