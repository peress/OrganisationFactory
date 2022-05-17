package com.pipedrive.challenge.service;

import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import com.pipedrive.challenge.model.OrganisationRequest;
import com.pipedrive.challenge.model.OrganisationResponse;

public interface OrganisationService {

    @Transactional
    void createOrganisation(OrganisationRequest organisation);

    @Transactional
    Set<OrganisationResponse> fetchAllOrganisationRelatioons(String organisationName, Integer pageNumber);
}
