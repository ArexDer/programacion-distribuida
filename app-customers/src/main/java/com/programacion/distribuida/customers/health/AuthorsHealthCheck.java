package com.programacion.distribuida.customers.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
// http://localhost:5050/q/health/live
public class AuthorsHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("app-customers-health")
                .withData("app-customers-health", "UP ->Esta ok")
                .up()
                .build();
    }
}


/*
revisar estae URL:

INTERFZ  DE LA SALUD: http://localhost:5050/q/health-ui esta revisar no sales
Health : http://localhost:5050/q/health    OK
VIVO : http://localhost:5050/q/health/live   OK
CORRIENDO: http://localhost:5050/q/health/ready   OK
 */