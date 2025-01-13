package tech.guilhermekaua.otisoftwaredesafiocep;

import org.springframework.boot.SpringApplication;

public class TestOtiSoftwareDesafioCepApplication {

	public static void main(String[] args) {
		SpringApplication.from(OtiSoftwareDesafioCepApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
