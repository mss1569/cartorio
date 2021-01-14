package com.mss1569.cartorio.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CartorioTest {

    @Test
    void getterAndSetterId() {
        Cartorio cartorio = Cartorio.builder().build();

        cartorio.setId(3L);
        Assertions.assertThat(cartorio.getId()).isEqualTo(3L);

        cartorio.setId(15L);
        Assertions.assertThat(cartorio.getId()).isEqualTo(15L);
    }

    @Test
    void getterAndSetterNome() {
        Cartorio cartorio = Cartorio.builder().build();

        cartorio.setNome("cartorio xyz");
        Assertions.assertThat(cartorio.getNome()).isEqualTo("cartorio xyz");

        cartorio.setNome("cartorio abc");
        Assertions.assertThat(cartorio.getNome()).isEqualTo("cartorio abc");
    }

    @Test
    void getterAndSetterEndereco() {
        Cartorio cartorio = Cartorio.builder().build();

        cartorio.setEndereco("rua x");
        Assertions.assertThat(cartorio.getEndereco()).isEqualTo("rua x");

        cartorio.setEndereco("rua y");
        Assertions.assertThat(cartorio.getEndereco()).isEqualTo("rua y");
    }
}