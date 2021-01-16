package com.mss1569.cartorio.service;

import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.repository.NotaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NotaryService {
    @Autowired
    private NotaryRepository notaryRepository;

    public Page<Notary> listAll(Pageable pageable) {
        return notaryRepository.findAll(pageable);
    }

    public Notary findById(long id) {
        return notaryRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Notary not found!"));
    }

    @Transactional
    public Notary save(Notary notary) {
        return notaryRepository.save(notary);
    }

    public void delete(long id) {
        notaryRepository.delete(findById(id));
    }

    public Notary update(Long notaryId, Notary notary) {
        notary.setId(findById(notaryId).getId());
        return notaryRepository.save(notary);
    }
}
