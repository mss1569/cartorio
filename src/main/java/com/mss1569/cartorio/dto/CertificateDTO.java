package com.mss1569.cartorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CertificateDTO {
    @NotEmpty(message = "Name cannot be empty!")
    @Schema(description = "Name of certificate", example = "certificate X")
    private String name;
}
