package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.repository.CertificateRepository;
import com.mss1569.cartorio.repository.NotaryRepository;
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
class CertificateControllerTest {
    private final String basePath = "/api/v1/certificates";

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private NotaryRepository notaryRepository;

    private Certificate certificate;

    @BeforeEach
    void setUp() {
        Notary notary = Notary.builder()
                .name("notary x")
                .address("street t")
                .build();
        notary = notaryRepository.save(notary);

        certificate = Certificate.builder()
                .name("certificate k")
                .notary(notary)
                .build();
    }

    @Test
    void listAll() {
        Certificate certificateSaved = certificateRepository.save(certificate);

        PageableResponse<Certificate> certificatesPage = testRestTemplate.exchange(basePath, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Certificate>>() {
                }).getBody();

        Assertions.assertThat(certificatesPage).isNotNull();

        Assertions.assertThat(certificatesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(certificatesPage.toList().get(0).getName()).isEqualTo(certificateSaved.getName());
    }

    @Test
    void findById() {
        Certificate certificateSaved = certificateRepository.save(certificate);

        Certificate certificateFound = testRestTemplate.getForObject(basePath + "/{id}", Certificate.class, certificateSaved.getId());

        Assertions.assertThat(certificateFound).isNotNull();
        Assertions.assertThat(certificateFound.getId()).isNotNull().isEqualTo(certificateSaved.getId());
    }

    @Test
    void update() {
        Certificate certificateSaved = certificateRepository.save(certificate);
        certificateSaved.setName("certificate l");

        CertificateDTO certificateDTO = modelMapper.map(certificateSaved, CertificateDTO.class);

        Certificate certificateResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(certificateDTO),
                new ParameterizedTypeReference<Certificate>() {
                },
                certificateSaved.getId()).getBody();

        Assertions.assertThat(certificateResponse).isNotNull();
        Assertions.assertThat(certificateResponse.getName()).isNotNull().isEqualTo(certificateDTO.getName());
    }

    @Test
    void delete() {
        Certificate certificateSaved = certificateRepository.save(certificate);

        ResponseEntity<Void> certificateResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.DELETE, null, Void.class, certificateSaved.getId());

        Assertions.assertThat(certificateResponse).isNotNull();
        Assertions.assertThat(certificateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}