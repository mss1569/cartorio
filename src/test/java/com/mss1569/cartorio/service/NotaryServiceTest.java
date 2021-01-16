package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.exception.BadRequestException;
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
class NotaryServiceTest {
    @InjectMocks
    private NotaryService notaryService;
    @Mock
    private NotaryRepository notaryRepository;

    private Notary notary;

    @BeforeEach
    void setUp() {
        notary = Notary.builder()
                .id(1L)
                .name("notary y")
                .address("street x")
                .build();

        PageImpl<Notary> notaryPageImpl = new PageImpl<>(List.of(notary));
        BDDMockito.when(notaryRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(notaryPageImpl);

        BDDMockito.when(notaryRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(notary));

        BDDMockito.when(notaryRepository.save(ArgumentMatchers.any(Notary.class)))
                .thenReturn(notary);

        BDDMockito.doNothing().when(notaryRepository).delete(ArgumentMatchers.any(Notary.class));
    }

    @Test
    void listAll() {
        Page<Notary> notaryPage = notaryService.listAll(PageRequest.of(0, 1));

        Assertions.assertThat(notaryPage).isNotNull();

        Assertions.assertThat(notaryPage.toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findById() {
        Notary notaryFound = notaryService.findById(1L);

        Assertions.assertThat(notaryFound).isNotNull();

        Assertions.assertThat(notaryFound.getId()).isNotNull().isEqualTo(notary.getId());
    }

    @Test
    void findByIdNotFound() {
        BDDMockito.when(notaryRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> notaryService.findById(1L));
    }

    @Test
    void save() {
        Assertions.assertThat(notaryService.save(notary))
                .isNotNull().isEqualTo(notary);
    }

    @Test
    void delete() {
        Assertions.assertThatCode(() -> notaryService.delete(notary.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void update() {
        Assertions.assertThat(notaryService.update(notary.getId(), notary))
                .isNotNull().isEqualTo(notary);
    }
}