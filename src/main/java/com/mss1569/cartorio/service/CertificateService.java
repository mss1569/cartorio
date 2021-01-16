package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.CertificateRepository;
import com.mss1569.cartorio.repository.NotaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CertificateService {
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private NotaryRepository notaryRepository;

    public Page<Certificate> listAll(Pageable pageable) {
        return certificateRepository.findAll(pageable);
    }

    public Page<Certificate> listAllByNotaryId(Long notaryId, Pageable pageable) {
        return certificateRepository.findByNotaryId(notaryId, pageable);
    }

    public Certificate findById(long id) {
        return certificateRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Certificate not found!"));
    }

    public Certificate findByIdAndNotaryId(Long id, Long notaryId) {
        return certificateRepository
                .findByIdAndNotaryId(id, notaryId)
                .orElseThrow(() -> new BadRequestException("Certificate not found!"));
    }

    @Transactional
    public Certificate save(Long notaryId, Certificate certificate) {
        Notary notary = notaryRepository
                .findById(notaryId)
                .orElseThrow(() -> new BadRequestException("Notary not found!"));

        certificate.setNotary(notary);
        return certificateRepository.save(certificate);
    }

    public void delete(long id) {
        certificateRepository.delete(findById(id));
    }

    public Certificate update(Long certificateId, Certificate certificate) {
        Certificate certificateOld = findById(certificateId);
        certificate.setId(certificateOld.getId());
        certificate.setNotary(certificateOld.getNotary());
        return certificateRepository.save(certificate);
    }
}