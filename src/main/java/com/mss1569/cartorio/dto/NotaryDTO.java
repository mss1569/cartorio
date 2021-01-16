package com.mss1569.cartorio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class NotaryDTO {
    @NotEmpty(message = "Name cannot be empty!")
    @Schema(description = "Name of notary", example = "notary X")
    private String name;
    @NotEmpty(message = "Address cannot be empty!")
    @Schema(description = "Address of notary", example = "street Y")
    private String address;
}