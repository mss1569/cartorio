package com.mss1569.cartorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CertificateDTO {
    @NotEmpty(message = "Name cannot be empty!")
    private String name;
}
