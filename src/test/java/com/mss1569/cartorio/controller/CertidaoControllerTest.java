package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.dto.CertidaoDTO;
import com.mss1569.cartorio.repository.CartorioRepository;
import com.mss1569.cartorio.repository.CertidaoRepository;
import com.mss1569.cartorio.wrapper.PageableResponse;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CertidaoControllerTest {
    private final String basePath = "/api/v1/certidoes";

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CertidaoRepository certidaoRepository;
    @Autowired
    private CartorioRepository cartorioRepository;

    private Cartorio cartorio;
    private Certidao certidao;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .nome("cartorio x")
                .endereco("rua t")
                .build();
        cartorio = cartorioRepository.save(cartorio);

        certidao = Certidao.builder()
                .nome("certidao k")
                .cartorio(cartorio)
                .build();
    }

    @Test
    void listAll() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        PageableResponse<Certidao> certidoesPage = testRestTemplate.exchange(basePath, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Certidao>>() {
                }).getBody();

        Assertions.assertThat(certidoesPage).isNotNull();

        Assertions.assertThat(certidoesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(certidoesPage.toList().get(0).getNome()).isEqualTo(certidaoSaved.getNome());
    }

    @Test
    void findById() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        Certidao certidaoFound = testRestTemplate.getForObject(basePath + "/{id}", Certidao.class, certidaoSaved.getId());

        Assertions.assertThat(certidaoFound).isNotNull();
        Assertions.assertThat(certidaoFound.getId()).isNotNull().isEqualTo(certidaoSaved.getId());
    }

    @Test
    void replace() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);
        certidaoSaved.setNome("certidao l");

        CertidaoDTO certidaoDTO = modelMapper.map(certidaoSaved, CertidaoDTO.class);

        Certidao certidaoResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(certidaoDTO),
                new ParameterizedTypeReference<Certidao>() {
                },
                certidaoSaved.getId()).getBody();

        Assertions.assertThat(certidaoResponse).isNotNull();
        Assertions.assertThat(certidaoResponse.getNome()).isNotNull().isEqualTo(certidaoDTO.getNome());
    }

    @Test
    void delete() {
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        ResponseEntity<Void> certidaoResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.DELETE, null, Void.class, certidaoSaved.getId());

        Assertions.assertThat(certidaoResponse).isNotNull();
        Assertions.assertThat(certidaoResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}