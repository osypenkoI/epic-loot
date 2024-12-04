package com.e_commerce.epic_loot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EpicLootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpicLootApplication.class, args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("https://lively-salamander-718c8f.netlify.app") // URL вашого фронту
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Методи, які дозволяються
                    .allowCredentials(true); // Якщо вам потрібні куки або автентифікація
        }
    }
}
