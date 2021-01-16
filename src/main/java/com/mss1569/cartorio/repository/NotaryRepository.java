package com.mss1569.cartorio.repository;

import com.mss1569.cartorio.domain.Notary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaryRepository extends JpaRepository<Notary, Long> {

}
