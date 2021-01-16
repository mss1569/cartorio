package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.service.CertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<Certificate> listAll(Pageable pageable) {
        return certificateService.listAll(pageable);
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<Certificate> findById(@PathVariable Long certificateId) {
        return new ResponseEntity<>(certificateService.findById(certificateId), HttpStatus.OK);
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<Certificate> update(@PathVariable Long certificateId,
                                              @Valid @RequestBody CertificateDTO certificateDTO) {
        return new ResponseEntity<>(certificateService.update(
                certificateId,
                modelMapper.map(certificateDTO, Certificate.class)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Certificate> delete(@PathVariable Long certificateId) {
        certificateService.delete(certificateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
