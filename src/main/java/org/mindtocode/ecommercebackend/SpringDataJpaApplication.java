package org.mindtocode.ecommercebackend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        // Only load .env file for local development
        // In prod/stg (Docker/K8s/cloud), env vars are provided by the platform
        String activeProfile = System.getProperty("spring.profiles.active",
                System.getenv("SPRING_PROFILES_ACTIVE"));

        // Load .env only for dev profile or when profile is not explicitly set (local
        // dev)
        if (activeProfile == null || activeProfile.isEmpty() || activeProfile.contains("dev")) {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
        }
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

}
