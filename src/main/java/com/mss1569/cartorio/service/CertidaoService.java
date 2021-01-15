package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CartorioRepository;
import com.mss1569.cartorio.repository.CertidaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CertidaoService {
    @Autowired
    private CertidaoRepository certidaoRepository;
    @Autowired
    private CartorioRepository cartorioRepository;

    public Page<Certidao> listAll(Pageable pageable) {
        return certidaoRepository.findAll(pageable);
    }

    public Page<Certidao> listAllByCartorioId(Long cartorioId, Pageable pageable) {
        return certidaoRepository.findByCartorioId(cartorioId, pageable);
    }

    public Certidao findById(long id) {
        return certidaoRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Certidao não encontrado!"));
    }

    public Certidao findByIdAndCartorioId(Long id, Long cartorioId) {
        return certidaoRepository
                .findByIdAndCartorioId(id, cartorioId)
                .orElseThrow(() -> new BadRequestException("Certidao não encontrado!"));
    }

    @Transactional
    public Certidao save(Long cartorioId, Certidao certidao) {
        Cartorio cartorio = cartorioRepository
                .findById(cartorioId)
                .orElseThrow(() -> new BadRequestException("Cartorio não encontrado!"));

        certidao.setCartorio(cartorio);
        return certidaoRepository.save(certidao);
    }

    public void delete(long id) {
        certidaoRepository.delete(findById(id));
    }

    public Certidao replace(Long certidaoId, Certidao certidao) {
        Certidao certidaoOld = findById(certidaoId);
        certidao.setId(certidaoOld.getId());
        certidao.setCartorio(certidaoOld.getCartorio());
        return certidaoRepository.save(certidao);
    }
}