package tqs_project.DETICafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tqs_project.DETICafe")
public class DetiCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetiCafeApplication.class, args);
	}

}
