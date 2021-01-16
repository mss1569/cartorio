package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CertificateRepository;
import com.mss1569.cartorio.repository.NotaryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CertificateServiceTest {
    @InjectMocks
    private CertificateService certificateService;
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private NotaryRepository notaryRepository;

    private Notary notary;
    private Certificate certificate;

    @BeforeEach
    void setUp() {
        notary = Notary.builder()
                .id(1L)
                .name("notary y")
                .address("street x")
                .build();
        certificate = Certificate.builder()
                .id(1L)
                .name("certificate p")
                .notary(notary)
                .build();

        PageImpl<Certificate> certificatePageImpl = new PageImpl<>(List.of(certificate));
        BDDMockito.when(certificateRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(certificatePageImpl);

        BDDMockito.when(certificateRepository
                .findByNotaryId(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(certificatePageImpl);

        BDDMockito.when(certificateRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(certificate));

        BDDMockito.when(certificateRepository.findByIdAndNotaryId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(certificate));

        BDDMockito.when(notaryRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(notary));

        BDDMockito.when(certificateRepository.save(ArgumentMatchers.any(Certificate.class)))
                .thenReturn(certificate);

        BDDMockito.doNothing().when(certificateRepository).delete(ArgumentMatchers.any(Certificate.class));
    }

    @Test
    void listAll() {
        Page<Certificate> certificatePage = certificateService.listAll(PageRequest.of(0, 1));

        Assertions.assertThat(certificatePage).isNotNull();

        Assertions.assertThat(certificatePage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void listAllByNotaryId() {
        Page<Certificate> certificatePage = certificateService
                .listAllByNotaryId(notary.getId(), PageRequest.of(0, 1));

        Assertions.assertThat(certificatePage).isNotNull();

        Assertions.assertThat(certificatePage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findById() {
        Certificate certificateFound = certificateService.findById(certificate.getId());

        Assertions.assertThat(certificateFound).isNotNull();

        Assertions.assertThat(certificateFound.getId()).isNotNull().isEqualTo(certificate.getId());
    }

    @Test
    void findByIdNotFound() {
        BDDMockito.when(certificateRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certificateService.findById(9999L));
    }

    @Test
    void findByIdAndNotaryId() {
        Certificate certificateFound = certificateService.findByIdAndNotaryId(certificate.getId(), notary.getId());

        Assertions.assertThat(certificateFound).isNotNull();

        Assertions.assertThat(certificateFound.getId()).isNotNull().isEqualTo(certificate.getId());
    }

    @Test
    void findByIdAndNotaryIdNotFound() {
        BDDMockito.when(certificateRepository.findByIdAndNotaryId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certificateService.findByIdAndNotaryId(999L, 5555L));
    }

    @Test
    void save() {
        Assertions.assertThat(certificateService.save(notary.getId(), certificate))
                .isNotNull().isEqualTo(certificate);
    }

    @Test
    void saveNotaryNotFound() {

        BDDMockito.when(notaryRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certificateService.save(95848L, certificate));
    }

    @Test
    void delete() {
        Assertions.assertThatCode(() -> certificateService.delete(certificate.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void replace() {
        Assertions.assertThat(certificateService.update(certificate.getId(), certificate))
                .isNotNull().isEqualTo(certificate);
    }
}