package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Cartorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartorioRepository extends JpaRepository<Cartorio, Long> {

}
