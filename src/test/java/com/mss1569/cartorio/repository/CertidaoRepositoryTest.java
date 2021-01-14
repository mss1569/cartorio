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

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .nome("cartorio y")
                .endereco("rua x")
                .build();
        cartorioRepository.save(cartorio);
    }

    @AfterEach
    void cleanUp() {
        cartorioRepository.delete(cartorio);
    }

    @Test
    void save() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        Certidao certidaoSaved = certidaoRepository.save(certidaoToSave);

        Assertions.assertThat(certidaoSaved).isNotNull();
        Assertions.assertThat(certidaoSaved.getId()).isNotNull();
        Assertions.assertThat(certidaoSaved.getNome()).isEqualTo(certidaoToSave.getNome());
    }

    @Test
    void update() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();
        Certidao certidaoSaved = certidaoRepository.save(certidaoToSave);

        certidaoSaved.setNome("certidao rr");
        Certidao certidaoUpdated = certidaoRepository.save(certidaoSaved);

        Assertions.assertThat(certidaoUpdated).isNotNull();
        Assertions.assertThat(certidaoUpdated.getId()).isEqualTo(certidaoSaved.getId());
        Assertions.assertThat(certidaoUpdated.getNome()).isEqualTo(certidaoSaved.getNome());
    }

    @Test
    void delete() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        Certidao certidaoSaved = certidaoRepository.save(certidaoToSave);
        certidaoRepository.delete(certidaoSaved);

        Optional<Certidao> certidaoOptional = certidaoRepository.findById(certidaoSaved.getId());
        Assertions.assertThat(certidaoOptional).isEmpty();
    }

    @Test
    void findById() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        Certidao certidaoSaved = certidaoRepository.save(certidaoToSave);
        Optional<Certidao> certidaoOptional = certidaoRepository.findById(certidaoSaved.getId());

        Assertions.assertThat(certidaoOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        certidaoRepository.save(certidaoToSave);
        Optional<Certidao> certidaoOptional = certidaoRepository.findById(8888L);

        Assertions.assertThat(certidaoOptional).isEmpty();
    }

    @Test
    void findByCartorioId() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();
        certidaoRepository.save(certidaoToSave);

        Page<Certidao> certidaoPage = certidaoRepository.findByCartorioId(cartorio.getId(), PageRequest.of(0, 10));

        Assertions.assertThat(certidaoPage).isNotEmpty();
    }

    @Test
    void findByCartorioIdNotFound() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();
        certidaoRepository.save(certidaoToSave);

        Page<Certidao> certidaoPage = certidaoRepository.findByCartorioId(8888L, PageRequest.of(0, 10));

        Assertions.assertThat(certidaoPage).isEmpty();
    }

    @Test
    void findByIdAndCartorioId() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        certidaoRepository.save(certidaoToSave);
        Optional<Certidao> certidaoOptional = certidaoRepository.findByIdAndCartorioId(certidaoToSave.getId(), cartorio.getId());

        Assertions.assertThat(certidaoOptional).isNotEmpty();
        Assertions.assertThat(certidaoOptional.get().getNome()).isEqualTo(certidaoToSave.getNome());
    }

    @Test
    void findByIdAndCartorioIdNotFound() {
        Certidao certidaoToSave = Certidao.builder()
                .nome("certidao y")
                .cartorio(cartorio)
                .build();

        certidaoRepository.save(certidaoToSave);
        Optional<Certidao> certidaoOptional = certidaoRepository.findByIdAndCartorioId(8888L, 9999L);

        Assertions.assertThat(certidaoOptional).isEmpty();
    }
}