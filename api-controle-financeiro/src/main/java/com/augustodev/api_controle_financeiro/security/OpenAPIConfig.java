package com.augustodev.api_controle_financeiro.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("https://marvellous-luce-augustodev-f348a5bf.koyeb.app");
        server.description("Servidor da API de Controle Financeiro");

        return new OpenAPI()
                .addServersItem(server)
                .info(new Info()
                        .title("API de Controle Financeiro")
                );
    }

}
