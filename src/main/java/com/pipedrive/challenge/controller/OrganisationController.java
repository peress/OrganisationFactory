package com.pipedrive.challenge.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pipedrive.challenge.model.OrganisationRequest;
import com.pipedrive.challenge.model.OrganisationResponse;
import com.pipedrive.challenge.service.OrganisationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/organisations")
public class OrganisationController {

    private final OrganisationService organisationService;

    @Operation(summary = "Create a new organisation")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrganisation(@RequestBody @NonNull OrganisationRequest organisationRequest) {

        log.info("Request received - createOrganisation");

        organisationService.createOrganisation(organisationRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all relations from given organisation")
    @GetMapping(path = "/relations/{organisationName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrganisationResponse>> findOrganisation(@PathVariable String organisationName,
                                                       @RequestParam(defaultValue = "0") Integer pageNumber) {

        log.info("Request received - findOrganisation");

        return new ResponseEntity<>(organisationService.fetchAllOrganisationRelatioons(organisationName, pageNumber), HttpStatus.OK);
    }
}
