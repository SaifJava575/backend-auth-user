package com.auth.rest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Example API", description = "API for demonstrating OpenAPI with SpringDoc")
public class ExampleController {

    @GetMapping("/hello")
    @Operation(summary = "Say Hello", description = "Returns a hello message", security = @SecurityRequirement(name = "bearerAuth"))
    public String sayHello() {
        return "Hello, World!";
    }
}
