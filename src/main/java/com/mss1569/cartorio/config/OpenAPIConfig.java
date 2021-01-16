package com.mss1569.cartorio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Cartorio API",
                version = "v1",
                description = "API RESTful para controle de cartorios"
        )
)
public class OpenAPIConfig {
}
