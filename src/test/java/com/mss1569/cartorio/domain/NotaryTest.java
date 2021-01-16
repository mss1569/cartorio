package com.mss1569.cartorio.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class NotaryTest {

    private Notary notary;

    @BeforeEach
    void setUp() {
        notary = Notary.builder().build();
    }

    @Test
    void getterAndSetterId() {
        notary.setId(3L);
        Assertions.assertThat(notary.getId()).isEqualTo(3L);

        notary.setId(15L);
        Assertions.assertThat(notary.getId()).isEqualTo(15L);
    }

    @Test
    void getterAndSetterNome() {
        notary.setName("notary xyz");
        Assertions.assertThat(notary.getName()).isEqualTo("notary xyz");

        notary.setName("notary abc");
        Assertions.assertThat(notary.getName()).isEqualTo("notary abc");
    }

    @Test
    void getterAndSetterAddress() {
        notary.setAddress("street x");
        Assertions.assertThat(notary.getAddress()).isEqualTo("street x");

        notary.setAddress("street y");
        Assertions.assertThat(notary.getAddress()).isEqualTo("street y");
    }
}