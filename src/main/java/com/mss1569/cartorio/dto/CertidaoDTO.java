package com.mss1569.cartorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CertidaoDTO {
    @NotEmpty(message = "Nome não pode ser vazio!")
    private String nome;
}
