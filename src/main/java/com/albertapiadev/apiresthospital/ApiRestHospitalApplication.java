package com.albertapiadev.apiresthospital;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiRestHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestHospitalApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api Rest Hospital")
                        .version("1.0")
                        .description("App con Spring Boot 3 y Swagger")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Github").url("https://github.com/albertapiadev")));

    }

}
