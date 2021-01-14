package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@DataJpaTest
class CertidaoRepositoryTest {

    @Autowired
    private CertidaoRepository certidaoRepository;

    @Autowired
    private CartorioRepository cartorioRepository;
    private Cartorio cartorio;
    private Certidao certidao;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();
        cartorioRepository.save(cartorio);

        certidao = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();
    }

    @AfterEach
    void cleanUp() {
        cartorioRepository.delete(cartorio);
    }

    @Test
    void save() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        Assertions.assertThat(certidaoSaved).isNotNull();
        Assertions.assertThat(certidaoSaved.getId()).isNotNull();
        Assertions.assertThat(certidaoSaved.getNome()).isEqualTo(certidao.getNome());
    }

    @Test
    void update() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        certidaoSaved.setNome("certidao rr");
        Certidao certidaoUpdated = certidaoRepository.save(certidaoSaved);

        Assertions.assertThat(certidaoUpdated).isNotNull();
        Assertions.assertThat(certidaoUpdated.getId()).isEqualTo(certidaoSaved.getId());
        Assertions.assertThat(certidaoUpdated.getNome()).isEqualTo(certidaoSaved.getNome());
    }

    @Test
    void delete() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);
        certidaoRepository.delete(certidaoSaved);

        Optional<Certidao> certidaoOptional = certidaoRepository.findById(certidaoSaved.getId());
        Assertions.assertThat(certidaoOptional).isEmpty();
    }

    @Test
    void findById() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);
        Optional<Certidao> certidaoOptional = certidaoRepository.findById(certidaoSaved.getId());

        Assertions.assertThat(certidaoOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        certidaoRepository.save(certidao);
        Optional<Certidao> certidaoOptional = certidaoRepository.findById(8888L);

        Assertions.assertThat(certidaoOptional).isEmpty();
    }

    @Test
    void findByCartorioId() {
        certidaoRepository.save(certidao);

        Page<Certidao> certidaoPage = certidaoRepository.findByCartorioId(cartorio.getId(), PageRequest.of(0, 10));

        Assertions.assertThat(certidaoPage).isNotEmpty();
    }

    @Test
    void findByCartorioIdNotFound() {
        certidaoRepository.save(certidao);

        Page<Certidao> certidaoPage = certidaoRepository.findByCartorioId(8888L, PageRequest.of(0, 10));

        Assertions.assertThat(certidaoPage).isEmpty();
    }

    @Test
    void findByIdAndCartorioId() {
        certidaoRepository.save(certidao);
        Optional<Certidao> certidaoOptional = certidaoRepository.findByIdAndCartorioId(certidao.getId(), cartorio.getId());

        Assertions.assertThat(certidaoOptional).isNotEmpty();
        Assertions.assertThat(certidaoOptional.get().getNome()).isEqualTo(certidao.getNome());
    }

    @Test
    void findByIdAndCartorioIdNotFound() {
        certidaoRepository.save(certidao);
        Optional<Certidao> certidaoOptional = certidaoRepository.findByIdAndCartorioId(8888L, 9999L);

        Assertions.assertThat(certidaoOptional).isEmpty();
    }
}