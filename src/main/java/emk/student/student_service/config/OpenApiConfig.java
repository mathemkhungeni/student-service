package emk.student.student_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI studentServiceOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Student Management Service API")
                .description("API for managing student records")
                .version("1.0")
                .contact(new Contact().name("Student Service Team").email("support@example.com"))
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
        .servers(
            List.of(
                new Server().url("http://localhost:8080").description("Local Development Server")));
  }
}
