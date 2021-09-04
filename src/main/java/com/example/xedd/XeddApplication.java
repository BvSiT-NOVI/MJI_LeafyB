package com.example.xedd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class XeddApplication {

    public static void main(String[] args) {
        SpringApplication.run(XeddApplication.class, args);
    }

    //See  https://spring.io/guides/gs/rest-service-cors/
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/upload").allowedOrigins("**");
            }
        };
    }

}
