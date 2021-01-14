package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Certidao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertidaoRepository extends JpaRepository<Certidao, Long> {
    Page<Certidao> findByCartorioId(Long cartorioId, Pageable pageable);
    Optional<Certidao> findByIdAndCartorioId(Long id, Long cartorioId);
}
