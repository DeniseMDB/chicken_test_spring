package proyect.Farm.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Value("${Farm-Proyect.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(this.devUrl);
        devServer.setDescription("Developer Server");

        Contact contact = new Contact()
                .name("Proyecto Denise Du Bois - Farm")
                .url("https://github.com/DeniseMDB/chicken_test_spring")
                .email("denisedubois93@gmail.com");

        License mitLicense = new License()
                .name("Sin licencia")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Chicken test using Spring Boot")
                .version("1.0.0")
                .description("API para gestionar granja")
                .termsOfService("https://www.example.com/tos")
                .contact(contact)
                .license(mitLicense);

        return new OpenAPI()
                .info(info);
    }
}

