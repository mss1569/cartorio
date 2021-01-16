package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Page<Certificate> findByNotaryId(Long notaryId, Pageable pageable);
    Optional<Certificate> findByIdAndNotaryId(Long id, Long notaryId);
}
