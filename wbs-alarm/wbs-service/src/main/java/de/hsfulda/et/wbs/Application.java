package de.hsfulda.et.wbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final String CONTEXT_ROOT = "/wbs";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // :8080
    }
}