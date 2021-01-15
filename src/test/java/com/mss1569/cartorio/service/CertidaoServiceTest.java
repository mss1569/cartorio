package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CartorioRepository;
import com.mss1569.cartorio.repository.CertidaoRepository;
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
class CertidaoServiceTest {
    @InjectMocks
    private CertidaoService certidaoService;
    @Mock
    private CertidaoRepository certidaoRepository;
    @Mock
    private CartorioRepository cartorioRepository;

    private Cartorio cartorio;
    private Certidao certidao;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .id(1L)
                .nome("cartorio y")
                .endereco("rua x")
                .build();
        certidao = Certidao.builder()
                .id(1L)
                .nome("certidao p")
                .cartorio(cartorio)
                .build();

        PageImpl<Certidao> certidaoPageImpl = new PageImpl<>(List.of(certidao));
        BDDMockito.when(certidaoRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(certidaoPageImpl);

        BDDMockito.when(certidaoRepository
                .findByCartorioId(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(certidaoPageImpl);

        BDDMockito.when(certidaoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(certidao));

        BDDMockito.when(certidaoRepository.findByIdAndCartorioId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(certidao));

        BDDMockito.when(cartorioRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(cartorio));

        BDDMockito.when(certidaoRepository.save(ArgumentMatchers.any(Certidao.class)))
                .thenReturn(certidao);

        BDDMockito.doNothing().when(certidaoRepository).delete(ArgumentMatchers.any(Certidao.class));
    }

    @Test
    void listAll() {
        Page<Certidao> certidaoPage = certidaoService.listAll(PageRequest.of(0, 1));

        Assertions.assertThat(certidaoPage).isNotNull();

        Assertions.assertThat(certidaoPage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void listAllByCartorioId() {
        Page<Certidao> certidaoPage = certidaoService
                .listAllByCartorioId(cartorio.getId(), PageRequest.of(0, 1));

        Assertions.assertThat(certidaoPage).isNotNull();

        Assertions.assertThat(certidaoPage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findById() {
        Certidao certidaoFound = certidaoService.findById(certidao.getId());

        Assertions.assertThat(certidaoFound).isNotNull();

        Assertions.assertThat(certidaoFound.getId()).isNotNull().isEqualTo(certidao.getId());
    }

    @Test
    void findByIdNotFound() {
        BDDMockito.when(certidaoRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certidaoService.findById(9999L));
    }

    @Test
    void findByIdAndCartorioId() {
        Certidao certidaoFound = certidaoService.findByIdAndCartorioId(certidao.getId(), cartorio.getId());

        Assertions.assertThat(certidaoFound).isNotNull();

        Assertions.assertThat(certidaoFound.getId()).isNotNull().isEqualTo(certidao.getId());
    }

    @Test
    void findByIdAndCartorioIdNotFound() {
        BDDMockito.when(certidaoRepository.findByIdAndCartorioId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certidaoService.findByIdAndCartorioId(999L, 5555L));
    }

    @Test
    void save() {
        Assertions.assertThat(certidaoService.save(cartorio.getId(), certidao))
                .isNotNull().isEqualTo(certidao);
    }

    @Test
    void saveCartorioNotFound() {

        BDDMockito.when(cartorioRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> certidaoService.save(95848L, certidao));
    }

    @Test
    void delete() {
        Assertions.assertThatCode(() -> certidaoService.delete(certidao.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void replace() {
        Assertions.assertThat(certidaoService.replace(certidao.getId(), certidao))
                .isNotNull().isEqualTo(certidao);
    }
}