package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
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
class CertificateRepositoryTest {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private NotaryRepository notaryRepository;
    private Notary notary;
    private Certificate certificate;

    @BeforeEach
    void setUp() {
        notary = Notary.builder()
                .name("notary y")
                .address("street x")
                .build();
        notaryRepository.save(notary);

        certificate = Certificate.builder()
                .name("certificate y")
                .notary(notary)
                .build();
    }

    @AfterEach
    void cleanUp() {
        notaryRepository.delete(notary);
    }

    @Test
    void save() {
        Certificate certificateSaved = certificateRepository.save(certificate);

        Assertions.assertThat(certificateSaved).isNotNull();
        Assertions.assertThat(certificateSaved.getId()).isNotNull();
        Assertions.assertThat(certificateSaved.getName()).isEqualTo(certificate.getName());
    }

    @Test
    void update() {
        Certificate certificateSaved = certificateRepository.save(certificate);

        certificateSaved.setName("certificate rr");
        Certificate certificateUpdated = certificateRepository.save(certificateSaved);

        Assertions.assertThat(certificateUpdated).isNotNull();
        Assertions.assertThat(certificateUpdated.getId()).isEqualTo(certificateSaved.getId());
        Assertions.assertThat(certificateUpdated.getName()).isEqualTo(certificateSaved.getName());
    }

    @Test
    void delete() {
        Certificate certificateSaved = certificateRepository.save(certificate);
        certificateRepository.delete(certificateSaved);

        Optional<Certificate> certificateOptional = certificateRepository.findById(certificateSaved.getId());
        Assertions.assertThat(certificateOptional).isEmpty();
    }

    @Test
    void findById() {
        Certificate certificateSaved = certificateRepository.save(certificate);
        Optional<Certificate> certificateOptional = certificateRepository.findById(certificateSaved.getId());

        Assertions.assertThat(certificateOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        certificateRepository.save(certificate);
        Optional<Certificate> certificateOptional = certificateRepository.findById(8888L);

        Assertions.assertThat(certificateOptional).isEmpty();
    }

    @Test
    void findByNotaryId() {
        certificateRepository.save(certificate);

        Page<Certificate> certificatesPage = certificateRepository.findByNotaryId(notary.getId(), PageRequest.of(0, 10));

        Assertions.assertThat(certificatesPage).isNotEmpty();
    }

    @Test
    void findByNotaryIdNotFound() {
        certificateRepository.save(certificate);

        Page<Certificate> certificatePage = certificateRepository.findByNotaryId(8888L, PageRequest.of(0, 10));

        Assertions.assertThat(certificatePage).isEmpty();
    }

    @Test
    void findByIdAndNotaryId() {
        certificateRepository.save(certificate);
        Optional<Certificate> certificateOptional = certificateRepository.findByIdAndNotaryId(certificate.getId(), notary.getId());

        Assertions.assertThat(certificateOptional).isNotEmpty();
        Assertions.assertThat(certificateOptional.get().getName()).isEqualTo(certificate.getName());
    }

    @Test
    void findByIdAndNotaryIdNotFound() {
        certificateRepository.save(certificate);
        Optional<Certificate> certificateOptional = certificateRepository.findByIdAndNotaryId(8888L, 9999L);

        Assertions.assertThat(certificateOptional).isEmpty();
    }
}