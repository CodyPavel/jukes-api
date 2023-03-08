package com.jukes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Jukes REST API",
                description = "Jukes REST application - APIs v1.0",
                version = "1.0",
                contact = @Contact(
                        name = "Pavlo",
                        url = "https://www.linkedin.com/in/pavloliakhov/",
                        email = "lyahov.97@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        servers = @Server(url = "http://localhost:8080")
)
public class SwaggerConfig {

}
