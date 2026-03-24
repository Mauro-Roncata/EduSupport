package br.com.edusupport.edusupport.config;

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
                registry.addMapping("/api/**") // Aplica a regra para todas as nossas URLs

                        // Localhost:3000 e 5173 são as portas padrão do React/Vite
                        .allowedOrigins("http://localhost:3000", "http://localhost:5173")

                        // Métodos que o front pode usar
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // Permite que o Front-end envie cabeçalhos
                        .allowedHeaders("*")

                        .allowCredentials(true);
            }
        };
    }
}
