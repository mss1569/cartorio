package com.mss1569.cartorio.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CertidaoTest {

    private Certidao certidao;

    @BeforeEach
    void setUp() {
        certidao = Certidao.builder().build();
    }

    @Test
    void getterAndSetterId() {
        certidao.setId(3L);
        Assertions.assertThat(certidao.getId()).isEqualTo(3L);

        certidao.setId(15L);
        Assertions.assertThat(certidao.getId()).isEqualTo(15L);
    }

    @Test
    void getterAndSetterNome() {
        certidao.setNome("certidao xyz");
        Assertions.assertThat(certidao.getNome()).isEqualTo("certidao xyz");

        certidao.setNome("certidao abc");
        Assertions.assertThat(certidao.getNome()).isEqualTo("certidao abc");
    }

    @Test
    void getterAndSetterCartorio() {
        Cartorio cartorio1 = Cartorio.builder()
                .id(1L)
                .build();

        Cartorio cartorio2 = Cartorio.builder()
                .id(2L)
                .build();

        certidao.setCartorio(cartorio1);
        Assertions.assertThat(certidao.getCartorio()).isEqualTo(cartorio1);

        certidao.setCartorio(cartorio2);
        Assertions.assertThat(certidao.getCartorio()).isEqualTo(cartorio2);
    }
}