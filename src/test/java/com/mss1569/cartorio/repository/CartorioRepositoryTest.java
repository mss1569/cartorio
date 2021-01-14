package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Cartorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class CartorioRepositoryTest {
    @Autowired
    private CartorioRepository cartorioRepository;

    @Test
    void save() {
        Cartorio cartorioToSave = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();

        Cartorio cartorioSaved = cartorioRepository.save(cartorioToSave);

        Assertions.assertThat(cartorioSaved).isNotNull();
        Assertions.assertThat(cartorioSaved.getId()).isNotNull();
        Assertions.assertThat(cartorioSaved.getNome()).isEqualTo(cartorioToSave.getNome());
        Assertions.assertThat(cartorioSaved.getEndereco()).isEqualTo(cartorioToSave.getEndereco());
    }

    @Test
    void update() {
        Cartorio cartorioToSave = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();
        Cartorio cartorioSaved = cartorioRepository.save(cartorioToSave);

        cartorioSaved.setNome("cartorio rr");
        Cartorio cartorioUpdated = cartorioRepository.save(cartorioSaved);

        Assertions.assertThat(cartorioUpdated).isNotNull();
        Assertions.assertThat(cartorioUpdated.getId()).isEqualTo(cartorioSaved.getId());
        Assertions.assertThat(cartorioUpdated.getNome()).isEqualTo(cartorioSaved.getNome());
    }

    @Test
    void delete() {
        Cartorio cartorioToSave = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();

        Cartorio cartorioSaved = cartorioRepository.save(cartorioToSave);
        cartorioRepository.delete(cartorioSaved);

        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(cartorioSaved.getId());
        Assertions.assertThat(cartorioOptional).isEmpty();
    }

    @Test
    void findById() {
        Cartorio cartorioToSave = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();

        Cartorio cartorioSaved = cartorioRepository.save(cartorioToSave);
        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(cartorioSaved.getId());

        Assertions.assertThat(cartorioOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        Cartorio cartorioToSave = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();

        cartorioRepository.save(cartorioToSave);
        Optional<Cartorio> cartorioOptional = cartorioRepository.findById(8888L);

        Assertions.assertThat(cartorioOptional).isEmpty();
    }
}