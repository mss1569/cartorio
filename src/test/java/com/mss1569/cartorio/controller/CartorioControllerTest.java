package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.dto.CartorioDTO;
import com.mss1569.cartorio.dto.CertidaoDTO;
import com.mss1569.cartorio.repository.CartorioRepository;
import com.mss1569.cartorio.repository.CertidaoRepository;
import com.mss1569.cartorio.wrapper.PageableResponse;
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
class CartorioControllerTest {
    private final String basePath = "/api/v1/cartorios";

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartorioRepository cartorioRepository;
    @Autowired
    private CertidaoRepository certidaoRepository;

    private Cartorio cartorio;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .nome("cartorio x")
                .endereco("rua t")
                .build();
    }

    @Test
    void listAll() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        PageableResponse<Cartorio> cartoriosPage = testRestTemplate.exchange(basePath, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Cartorio>>() {
                }).getBody();

        Assertions.assertThat(cartoriosPage).isNotNull();

        Assertions.assertThat(cartoriosPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(cartoriosPage.toList().get(0).getNome()).isEqualTo(cartorioSaved.getNome());
    }

    @Test
    void findById() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        Cartorio cartorioFound = testRestTemplate.getForObject(basePath + "/{id}", Cartorio.class, cartorioSaved.getId());

        Assertions.assertThat(cartorioFound).isNotNull();
        Assertions.assertThat(cartorioFound.getId()).isNotNull().isEqualTo(cartorioSaved.getId());
    }

    @Test
    void save() {
        CartorioDTO cartorioDTO = modelMapper.map(cartorio, CartorioDTO.class);

        Cartorio cartorioSaved = testRestTemplate.postForEntity(basePath, cartorioDTO, Cartorio.class).getBody();

        Assertions.assertThat(cartorioSaved).isNotNull();
        Assertions.assertThat(cartorioSaved.getNome()).isNotNull().isEqualTo(cartorioDTO.getNome());
    }

    @Test
    void replace() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);
        cartorioSaved.setNome("cartorio j");

        CartorioDTO cartorioDTO = modelMapper.map(cartorioSaved, CartorioDTO.class);

        Cartorio cartorioResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(cartorioDTO),
                new ParameterizedTypeReference<Cartorio>() {
                },
                cartorioSaved.getId()).getBody();

        Assertions.assertThat(cartorioResponse).isNotNull();
        Assertions.assertThat(cartorioResponse.getNome()).isNotNull().isEqualTo(cartorioDTO.getNome());
    }

    @Test
    void delete() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        ResponseEntity<Void> cartorioResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.DELETE, null, Void.class, cartorioSaved.getId());

        Assertions.assertThat(cartorioResponse).isNotNull();
        Assertions.assertThat(cartorioResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void listAllCertidoesByCartorioId() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        Certidao certidao = Certidao.builder()
                .nome("certidao h")
                .cartorio(cartorio)
                .build();
        Certidao certidaoSaved = certidaoRepository.save(certidao);

        PageableResponse<Certidao> certidoesPage = testRestTemplate.exchange(
                basePath + "/{cartorioId}/certidoes",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Certidao>>() {
                },
                cartorioSaved.getId()).getBody();

        Assertions.assertThat(certidoesPage).isNotNull();

        Assertions.assertThat(certidoesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(certidoesPage.toList().get(0).getNome()).isEqualTo(certidaoSaved.getNome());
    }

    @Test
    void saveCertidao() {
        Cartorio cartorioSaved = cartorioRepository.save(cartorio);

        Certidao certidao = Certidao.builder()
                .nome("certidao h")
                .build();
        CertidaoDTO certidaoDTO = modelMapper.map(certidao, CertidaoDTO.class);

        Cartorio certidaoResponse = testRestTemplate.postForEntity(
                basePath + "/{cartorioId}/certidoes",
                certidaoDTO,
                Cartorio.class,
                cartorioSaved.getId()).getBody();

        Assertions.assertThat(certidaoResponse).isNotNull();
        Assertions.assertThat(certidaoResponse.getNome()).isNotNull().isEqualTo(certidaoDTO.getNome());
    }
}