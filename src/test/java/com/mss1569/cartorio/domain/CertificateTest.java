package com.mss1569.cartorio.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CertificateTest {

    private Certificate certificate;

    @BeforeEach
    void setUp() {
        certificate = Certificate.builder().build();
    }

    @Test
    void getterAndSetterId() {
        certificate.setId(3L);
        Assertions.assertThat(certificate.getId()).isEqualTo(3L);

        certificate.setId(15L);
        Assertions.assertThat(certificate.getId()).isEqualTo(15L);
    }

    @Test
    void getterAndSetterNome() {
        certificate.setName("certificate xyz");
        Assertions.assertThat(certificate.getName()).isEqualTo("certificate xyz");

        certificate.setName("certificate abc");
        Assertions.assertThat(certificate.getName()).isEqualTo("certificate abc");
    }

    @Test
    void getterAndSetterNotary() {
        Notary Notary1 = Notary.builder()
                .id(1L)
                .build();

        Notary Notary2 = Notary.builder()
                .id(2L)
                .build();

        certificate.setNotary(Notary1);
        Assertions.assertThat(certificate.getNotary()).isEqualTo(Notary1);

        certificate.setNotary(Notary2);
        Assertions.assertThat(certificate.getNotary()).isEqualTo(Notary2);
    }
}