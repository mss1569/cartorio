package com.mss1569.cartorio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class NotaryDTO {
    @NotEmpty(message = "Name cannot be empty!")
    private String name;
    @NotEmpty(message = "Address cannot be empty!")
    private String address;
}