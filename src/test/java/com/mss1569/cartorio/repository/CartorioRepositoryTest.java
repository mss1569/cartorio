package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Cartorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class CartorioRepositoryTest {
    @Autowired
    private CartorioRepository cartorioRepository;

    private Cartorio cartorio;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();
    }

    @Test
    void save() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        Assertions.assertThat(cartorioSaved).isNotNull();
        Assertions.assertThat(cartorioSaved.getId()).isNotNull();
        Assertions.assertThat(cartorioSaved.getNome()).isEqualTo(cartorio.getNome());
        Assertions.assertThat(cartorioSaved.getEndereco()).isEqualTo(cartorio.getEndereco());
    }

    @Test
    void update() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        cartorioSaved.setNome("cartorio rr");
        Cartorio cartorioUpdated = cartorioRepository.save(cartorioSaved);

        Assertions.assertThat(cartorioUpdated).isNotNull();
        Assertions.assertThat(cartorioUpdated.getId()).isEqualTo(cartorioSaved.getId());
        Assertions.assertThat(cartorioUpdated.getNome()).isEqualTo(cartorioSaved.getNome());
    }

    @Test
    void delete() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);
        cartorioRepository.delete(cartorioSaved);

        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(cartorioSaved.getId());
        Assertions.assertThat(cartorioOptional).isEmpty();
    }

    @Test
    void findById() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);
        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(cartorioSaved.getId());

        Assertions.assertThat(cartorioOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        cartorioRepository.save(cartorio);
        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(8888L);

        Assertions.assertThat(cartorioOptional).isEmpty();
    }
}