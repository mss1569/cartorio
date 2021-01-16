package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
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

    @GetMapping
    @Operation(summary = "List all certificates",
            tags = {"Certificate"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, return pageable list of certificates")
            })
    public Page<Certificate> listAll(@ParameterObject Pageable pageable) {
        return certificateService.listAll(pageable);
    }

    @GetMapping("/{certificateId}")
    @Operation(summary = "Find a certificate",
            tags = {"Certificate"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "certificateId", description = "Certificate Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, return certificate"),
                    @ApiResponse(responseCode = "400", description = "Certificate not found")
            })
    public ResponseEntity<Certificate> findById(@PathVariable Long certificateId) {
        return new ResponseEntity<>(certificateService.findById(certificateId), HttpStatus.OK);
    }

    @PutMapping("/{certificateId}")
    @Operation(summary = "Updates a certificate",
            tags = {"Certificate"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "certificateId", description = "Certificate Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, certificate updated,return certificate"),
                    @ApiResponse(responseCode = "400", description = "Certificate not found")
            })
    public ResponseEntity<Certificate> update(@PathVariable Long certificateId,
                                              @Valid @RequestBody CertificateDTO certificateDTO) {
        return new ResponseEntity<>(certificateService.update(
                certificateId,
                modelMapper.map(certificateDTO, Certificate.class)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{certificateId}")
    @Operation(summary = "Delete a certificate",
            tags = {"Certificate"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "certificateId", description = "Certificate Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, certificate deleted"),
                    @ApiResponse(responseCode = "400", description = "Certificate not found")
            })
    public ResponseEntity<Certificate> delete(@PathVariable Long certificateId) {
        certificateService.delete(certificateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
