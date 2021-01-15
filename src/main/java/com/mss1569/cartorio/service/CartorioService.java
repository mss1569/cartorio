package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CartorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartorioService {
    @Autowired
    private CartorioRepository cartorioRepository;

    public Page<Cartorio> listAll(Pageable pageable) {
        return cartorioRepository.findAll(pageable);
    }

    public Cartorio findById(long id) {
        return cartorioRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Cartorio não encontrado!"));
    }

    @Transactional
    public Cartorio save(Cartorio cartorio) {
        return cartorioRepository.save(cartorio);
    }

    public void delete(long id) {
        cartorioRepository.delete(findById(id));
    }

    public Cartorio replace(Long cartorioId, Cartorio cartorio) {
        cartorio.setId(findById(cartorioId).getId());
        return cartorioRepository.save(cartorio);
    }
}
