package com.mss1569.cartorio.controller;


import com.mss1569.cartorio.domain.Cartorio;
import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.dto.CartorioDTO;
import com.mss1569.cartorio.dto.CertidaoDTO;
import com.mss1569.cartorio.service.CartorioService;
import com.mss1569.cartorio.service.CertidaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/cartorios")
public class CartorioController {

    @Autowired
    private CartorioService cartorioService;
    @Autowired
    private CertidaoService certidaoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<Cartorio> listAll(Pageable pageable) {
        return cartorioService.listAll(pageable);
    }

    @GetMapping("/{cartorioId}")
    public ResponseEntity<Cartorio> findById(@PathVariable Long cartorioId) {
        return new ResponseEntity<>(cartorioService.findById(cartorioId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Cartorio> save(@Valid @RequestBody CartorioDTO cartorioDTO) {
        return new ResponseEntity<>(cartorioService.save(modelMapper.map(cartorioDTO, Cartorio.class)), HttpStatus.CREATED);
    }

    @PutMapping("/{cartorioId}")
    public ResponseEntity<Cartorio> replace(@PathVariable Long cartorioId,
                                            @Valid @RequestBody CartorioDTO cartorioDTO) {
        return new ResponseEntity<>(cartorioService.replace(cartorioId, modelMapper.map(cartorioDTO, Cartorio.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{cartorioId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartorioId) {
        cartorioService.delete(cartorioId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cartorioId}/certidoes")
    public Page<Certidao> listAllCertidoesByCartorioId(@PathVariable Long cartorioId,
                                                       Pageable pageable) {
        return certidaoService.listAllByCartorioId(cartorioId, pageable);
    }

    @PostMapping("/{cartorioId}/certidoes")
    public ResponseEntity<Certidao> saveCertidao(@PathVariable Long cartorioId,
                                                 @Valid @RequestBody CertidaoDTO certidaoDTO) {
        return new ResponseEntity<>(certidaoService.save(
                cartorioId,
                modelMapper.map(certidaoDTO, Certidao.class)),
                HttpStatus.CREATED);
    }
}
