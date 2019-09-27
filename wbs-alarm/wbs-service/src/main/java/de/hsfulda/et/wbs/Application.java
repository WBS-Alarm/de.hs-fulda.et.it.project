package de.hsfulda.et.wbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static final String CONTEXT_ROOT = "/wbs";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // :8080
    }

    // Override the configure method from the SpringBootServletInitializer class
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}