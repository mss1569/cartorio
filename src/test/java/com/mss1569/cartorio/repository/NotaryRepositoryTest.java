package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Notary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class NotaryRepositoryTest {
    @Autowired
    private NotaryRepository notaryRepository;

    private Notary notary;

    @BeforeEach
    void setUp() {
        notary = Notary.builder()
                .name("notary y")
                .address("street x")
                .build();
    }

    @Test
    void save() {
        Notary notarySaved = notaryRepository.save(notary);

        Assertions.assertThat(notarySaved).isNotNull();
        Assertions.assertThat(notarySaved.getId()).isNotNull();
        Assertions.assertThat(notarySaved.getName()).isEqualTo(notary.getName());
        Assertions.assertThat(notarySaved.getAddress()).isEqualTo(notary.getAddress());
    }

    @Test
    void update() {
        Notary notarySaved = notaryRepository.save(notary);

        notarySaved.setName("notary rr");
        Notary notaryUpdated = notaryRepository.save(notarySaved);

        Assertions.assertThat(notaryUpdated).isNotNull();
        Assertions.assertThat(notaryUpdated.getId()).isEqualTo(notarySaved.getId());
        Assertions.assertThat(notaryUpdated.getName()).isEqualTo(notarySaved.getName());
    }

    @Test
    void delete() {
        Notary notarySaved = notaryRepository.save(notary);
        notaryRepository.delete(notarySaved);

        Optional<Notary> notaryOptional = notaryRepository.findById(notarySaved.getId());
        Assertions.assertThat(notaryOptional).isEmpty();
    }

    @Test
    void findById() {
        Notary notarySaved = notaryRepository.save(notary);
        Optional<Notary> notaryOptional = notaryRepository.findById(notarySaved.getId());

        Assertions.assertThat(notaryOptional).isNotEmpty();
    }

    @Test
    void findByIdNotFound() {
        notaryRepository.save(notary);
        Optional<Notary> notaryOptional = notaryRepository.findById(8888L);

        Assertions.assertThat(notaryOptional).isEmpty();
    }
}