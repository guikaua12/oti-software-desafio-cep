package tech.guilhermekaua.otisoftwaredesafiocep;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.from(MainApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
