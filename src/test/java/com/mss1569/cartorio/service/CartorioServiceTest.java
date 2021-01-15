package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CartorioRepository;
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
class CartorioServiceTest {
    @InjectMocks
    private CartorioService cartorioService;
    @Mock
    private CartorioRepository cartorioRepository;

    private Cartorio cartorio;

    @BeforeEach
    void setUp() {
        cartorio = Cartorio.builder()
                .id(1L)
                .nome("cartorio y")
                .endereco("rua x")
                .build();

        PageImpl<Cartorio> cartorioPageImpl = new PageImpl<>(List.of(cartorio));
        BDDMockito.when(cartorioRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(cartorioPageImpl);

        BDDMockito.when(cartorioRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(cartorio));

        BDDMockito.when(cartorioRepository.save(ArgumentMatchers.any(Cartorio.class)))
                .thenReturn(cartorio);

        BDDMockito.doNothing().when(cartorioRepository).delete(ArgumentMatchers.any(Cartorio.class));
    }

    @Test
    void listAll() {
        Page<Cartorio> cartorioPage = cartorioService.listAll(PageRequest.of(0, 1));

        Assertions.assertThat(cartorioPage).isNotNull();

        Assertions.assertThat(cartorioPage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findById() {
        Cartorio cartorioFound = cartorioService.findById(1L);

        Assertions.assertThat(cartorioFound).isNotNull();

        Assertions.assertThat(cartorioFound.getId()).isNotNull().isEqualTo(cartorio.getId());
    }

    @Test
    void findByIdNotFound() {
        BDDMockito.when(cartorioRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> cartorioService.findById(1L));
    }

    @Test
    void save() {
        Assertions.assertThat(cartorioService.save(cartorio))
                .isNotNull().isEqualTo(cartorio);
    }

    @Test
    void delete() {
        Assertions.assertThatCode(() -> cartorioService.delete(cartorio.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void replace() {
        Assertions.assertThat(cartorioService.replace(cartorio.getId(), cartorio))
                .isNotNull().isEqualTo(cartorio);
    }
}