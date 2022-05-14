package com.pipedrive.challenge.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.pipedrive.challenge.entity.Organisation;
import com.pipedrive.challenge.model.OrganisationRequest;
import com.pipedrive.challenge.model.OrganisationResponse;
import com.pipedrive.challenge.model.RelationshipTypes;
import com.pipedrive.challenge.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Override
    public void createOrganisation(OrganisationRequest organisation) {
        Organisation org = new Organisation();
        org.setName("B");

        organisationRepository.save(org);
    }

    @Override
    public List<OrganisationResponse> fetchAllOrganisationRelatioons(String organisationName, Integer pageNumber) {
        return organisationRepository.findByNameOrderByNameDesc(
                        organisationName,
                        PageRequest.of(pageNumber, OrganisationRepository.PAGE_SIZE))
                .getContent()
                .stream()
                .map(organisation ->
                        OrganisationResponse.builder()
                                .name(organisation.getName())
                                .relationshipType(RelationshipTypes.DAUGHTER)
                                .build())
                .collect(Collectors.toList());
    }
}
