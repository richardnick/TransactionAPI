package com.richard.transactionapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "Transaction Service API ",
                 description = "A REST API For Dot Assessment",
                contact = @Contact (
                        name = "Chukwuemeka Nnaji",
                        email = "nchukwuemeka17@gmail.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "A REST API that Simulates Banking API"
        )
)
public class TransactionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApiApplication.class, args);
    }

}
