package com.mss1569.cartorio.controller;

import com.mss1569.cartorio.domain.Certificate;
import com.mss1569.cartorio.domain.Notary;
import com.mss1569.cartorio.dto.CertificateDTO;
import com.mss1569.cartorio.dto.NotaryDTO;
import com.mss1569.cartorio.exception.BadRequestException;
import com.mss1569.cartorio.service.CertificateService;
import com.mss1569.cartorio.service.NotaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping(path = "/api/v1/notaries")
public class NotaryController {

    @Autowired
    private NotaryService notaryService;
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "List all notaries",
            tags = {"Notary"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, return pageable list of notaries")
            })
    public Page<Notary> listAll(@ParameterObject Pageable pageable) {
        return notaryService.listAll(pageable);
    }

    @GetMapping("/{notaryId}")
    @Operation(summary = "Find a notary",
            tags = {"Notary"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "notaryId", description = "Notary Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, return notary"),
                    @ApiResponse(responseCode = "400", description = "Notary not found", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
            })
    public ResponseEntity<Notary> findById(@PathVariable Long notaryId) {
        return new ResponseEntity<>(notaryService.findById(notaryId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Creates a notary",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful operation, notary created, return notary"),
            })
    public ResponseEntity<Notary> save(@Valid @RequestBody NotaryDTO notaryDTO) {
        return new ResponseEntity<>(notaryService.save(modelMapper.map(notaryDTO, Notary.class)), HttpStatus.CREATED);
    }

    @PutMapping("/{notaryId}")
    @Operation(summary = "Updates a notary",
            tags = {"Notary"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "notaryId", description = "Notary Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, notary updated,return notary"),
                    @ApiResponse(responseCode = "400", description = "Notary not found", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
            })
    public ResponseEntity<Notary> update(@PathVariable Long notaryId,
                                         @Valid @RequestBody NotaryDTO notaryDTO) {
        return new ResponseEntity<>(notaryService.update(notaryId, modelMapper.map(notaryDTO, Notary.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{notaryId}")
    @Operation(summary = "Delete a notary",
            tags = {"Notary"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "notaryId", description = "Notary Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, notary deleted"),
                    @ApiResponse(responseCode = "400", description = "Notary not found", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
            })
    public ResponseEntity<Void> delete(@PathVariable Long notaryId) {
        notaryService.delete(notaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{notaryId}/certificates")
    @Operation(summary = "Lists all certificates from a notary",
            tags = {"Notary"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "notaryId", description = "Notary Id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation, return all certificates from a notary"),
                    @ApiResponse(responseCode = "400", description = "Notary not found", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
            })
    public Page<Certificate> listAllCertificatesByNotaryId(@PathVariable Long notaryId,
                                                           @ParameterObject Pageable pageable) {
        return certificateService.listAllByNotaryId(notaryId, pageable);
    }

    @PostMapping("/{notaryId}/certificates")
    @Operation(summary = "Emit a certificate to a notary",
            tags = {"Notary"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "notaryId", description = "Notary Id")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful operation, certificate emitted, return certificate"),
                    @ApiResponse(responseCode = "400", description = "Notary not found", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
            })
    public ResponseEntity<Certificate> saveCertificate(@PathVariable Long notaryId,
                                                       @Valid @RequestBody CertificateDTO certificateDTO) {
        return new ResponseEntity<>(certificateService.save(
                notaryId,
                modelMapper.map(certificateDTO, Certificate.class)),
                HttpStatus.CREATED);
    }
}
