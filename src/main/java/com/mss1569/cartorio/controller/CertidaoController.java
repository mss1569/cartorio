package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certidao;
import com.mss1569.cartorio.dto.CertidaoDTO;
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
@RequestMapping(path = "api/v1/certidoes")
public class CertidaoController {

    @Autowired
    private CertidaoService certidaoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<Certidao> listAll(Pageable pageable) {
        return certidaoService.listAll(pageable);
    }

    @GetMapping("/{certidaoId}")
    public ResponseEntity<Certidao> findById(@PathVariable Long certidaoId) {
        return new ResponseEntity<>(certidaoService.findById(certidaoId), HttpStatus.OK);
    }

    @PutMapping("/{certidaoId}")
    public ResponseEntity<Certidao> replace(@PathVariable Long certidaoId,
                                            @Valid @RequestBody CertidaoDTO certidaoDTO) {
        return new ResponseEntity<>(certidaoService.replace(
                certidaoId,
                modelMapper.map(certidaoDTO, Certidao.class)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{certidaoId}")
    public ResponseEntity<Certidao> delete(@PathVariable Long certidaoId) {
        certidaoService.delete(certidaoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
