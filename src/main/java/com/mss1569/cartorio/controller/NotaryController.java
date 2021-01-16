package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.dto.NotaryDTO;
import com.mss1569.cartorio.service.CertificateService;
import com.mss1569.cartorio.service.NotaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/notaries")
public class NotaryController {

    @Autowired
    private NotaryService notaryService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public Page<Notary> listAll(Pageable pageable) {
        return notaryService.listAll(pageable);
    }

    @GetMapping("/{notaryId}")
    public ResponseEntity<Notary> findById(@PathVariable Long notaryId) {
        return new ResponseEntity<>(notaryService.findById(notaryId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Notary> save(@Valid @RequestBody NotaryDTO notaryDTO) {
        return new ResponseEntity<>(notaryService.save(modelMapper.map(notaryDTO, Notary.class)), HttpStatus.CREATED);
    }

    @PutMapping("/{notaryId}")
    public ResponseEntity<Notary> update(@PathVariable Long notaryId,
                                         @Valid @RequestBody NotaryDTO notaryDTO) {
        return new ResponseEntity<>(notaryService.update(notaryId, modelMapper.map(notaryDTO, Notary.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{notaryId}")
    public ResponseEntity<Void> delete(@PathVariable Long notaryId) {
        notaryService.delete(notaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{notaryId}/certificates")
    public Page<Certificate> listAllCertificatesByNotaryId(@PathVariable Long notaryId,
                                                           Pageable pageable) {
        return certificateService.listAllByNotaryId(notaryId, pageable);
    }

    @PostMapping("/{notaryId}/certificates")
    public ResponseEntity<Certificate> saveCertificate(@PathVariable Long notaryId,
                                                       @Valid @RequestBody CertificateDTO certificateDTO) {
        return new ResponseEntity<>(certificateService.save(
                notaryId,
                modelMapper.map(certificateDTO, Certificate.class)),
                HttpStatus.CREATED);
    }
}
