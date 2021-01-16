package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.dto.NotaryDTO;
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
class NotaryControllerTest {
    private final String basePath = "/api/v1/notaries";

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NotaryRepository notaryRepository;
    @Autowired
    private CertificateRepository certificateRepository;

    private Notary notary;

    @BeforeEach
    void setUp() {
        notary = Notary.builder()
                .name("notary x")
                .address("street t")
                .build();
    }

    @Test
    void listAll() {
        Notary notarySaved = notaryRepository.save(notary);

        PageableResponse<Notary> notariesPage = testRestTemplate.exchange(basePath, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Notary>>() {
                }).getBody();

        Assertions.assertThat(notariesPage).isNotNull();

        Assertions.assertThat(notariesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(notariesPage.toList().get(0).getName()).isEqualTo(notarySaved.getName());
    }

    @Test
    void findById() {
        Notary notarySaved = notaryRepository.save(notary);

        Notary notaryFound = testRestTemplate.getForObject(basePath + "/{id}", Notary.class, notarySaved.getId());

        Assertions.assertThat(notaryFound).isNotNull();
        Assertions.assertThat(notaryFound.getId()).isNotNull().isEqualTo(notarySaved.getId());
    }

    @Test
    void save() {
        NotaryDTO notaryDTO = modelMapper.map(notary, NotaryDTO.class);

        Notary notarySaved = testRestTemplate.postForEntity(basePath, notaryDTO, Notary.class).getBody();

        Assertions.assertThat(notarySaved).isNotNull();
        Assertions.assertThat(notarySaved.getName()).isNotNull().isEqualTo(notaryDTO.getName());
    }

    @Test
    void update() {
        Notary notarySaved = notaryRepository.save(notary);
        notarySaved.setName("notary j");

        NotaryDTO notaryDTO = modelMapper.map(notarySaved, NotaryDTO.class);

        Notary notaryResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(notaryDTO),
                new ParameterizedTypeReference<Notary>() {
                },
                notarySaved.getId()).getBody();

        Assertions.assertThat(notaryResponse).isNotNull();
        Assertions.assertThat(notaryResponse.getName()).isNotNull().isEqualTo(notaryDTO.getName());
    }

    @Test
    void delete() {
        Notary notarySaved = notaryRepository.save(notary);

        ResponseEntity<Void> notaryResponse = testRestTemplate.exchange(basePath + "/{id}",
                HttpMethod.DELETE, null, Void.class, notarySaved.getId());

        Assertions.assertThat(notaryResponse).isNotNull();
        Assertions.assertThat(notaryResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void listAllCertificatesByNotaryId() {
        Notary notarySaved = notaryRepository.save(notary);

        Certificate certificate = Certificate.builder()
                .name("certificate h")
                .notary(notary)
                .build();
        Certificate certificateSaved = certificateRepository.save(certificate);

        PageableResponse<Certificate> certificatesPage = testRestTemplate.exchange(
                basePath + "/{notaryId}/certificates",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Certificate>>() {
                },
                notarySaved.getId()).getBody();

        Assertions.assertThat(certificatesPage).isNotNull();

        Assertions.assertThat(certificatesPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(certificatesPage.toList().get(0).getName()).isEqualTo(certificateSaved.getName());
    }

    @Test
    void saveCertificate() {
        Notary notarySaved = notaryRepository.save(notary);

        Certificate certificate = Certificate.builder()
                .name("certificate h")
                .build();
        CertificateDTO certificateDTO = modelMapper.map(certificate, CertificateDTO.class);

        Certificate certificateResponse = testRestTemplate.postForEntity(
                basePath + "/{notaryId}/certificates",
                certificateDTO,
                Certificate.class,
                notarySaved.getId()).getBody();

        Assertions.assertThat(certificateResponse).isNotNull();
        Assertions.assertThat(certificateResponse.getName()).isNotNull().isEqualTo(certificateDTO.getName());
    }
}