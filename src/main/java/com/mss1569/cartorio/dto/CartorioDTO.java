package com.mss1569.cartorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CartorioDTO {
    @NotEmpty(message = "Nome não pode ser vazio!")
    private String nome;
    @NotEmpty(message = "Endereco não pode ser vazio!")
    private String endereco;
}