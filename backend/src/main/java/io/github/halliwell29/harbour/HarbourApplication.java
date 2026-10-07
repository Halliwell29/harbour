package io.github.halliwell29.harbour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HarbourApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarbourApplication.class, args);
    }

}
