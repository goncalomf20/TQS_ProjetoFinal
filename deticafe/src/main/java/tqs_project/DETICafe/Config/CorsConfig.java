package tqs_project.DETICafe.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS for all endpoints
                       .allowCredentials(true)
                       .allowedOrigins("http://localhost:5173", "http://localhost:8080") // Allow all origins
                       .allowedHeaders("*") // Allow all headers
                       .allowedMethods("GET", "POST", "PUT", "DELETE"); // Allow all methods
            }
        };
    }
}

