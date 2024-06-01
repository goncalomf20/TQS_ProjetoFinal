package tqs_project.deticafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tqs_project.deticafe")
public class DetiCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetiCafeApplication.class, args);
	}

}
