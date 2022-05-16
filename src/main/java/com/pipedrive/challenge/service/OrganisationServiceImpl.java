package com.pipedrive.challenge.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.pipedrive.challenge.entity.Organisation;
import com.pipedrive.challenge.entity.OrganisationId;
import com.pipedrive.challenge.model.OrganisationRequest;
import com.pipedrive.challenge.model.OrganisationResponse;
import com.pipedrive.challenge.repository.OrganisationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Override
    public void createOrganisation(OrganisationRequest organisations) {
        Set<Organisation> organisationSet = new HashSet<>();
        findAllOrganisations(organisationSet, null, organisations);

        organisationRepository.saveAll(organisationSet);
    }

    @Override
    public List<OrganisationResponse> fetchAllOrganisationRelatioons(String organisationName, Integer pageNumber) {
        return organisationRepository.findOrganisationRelationships(
                        organisationName,
                        PageRequest.of(pageNumber, OrganisationRepository.PAGE_SIZE))
                .getContent()
                .stream()
                .map(organisation ->
                        OrganisationResponse.builder()
                                .name(organisation.getName())
                                .relationshipType(organisation.getRelationship())
                                .build())
                .collect(Collectors.toList());
    }

    private void findAllOrganisations(Set<Organisation> organisations, String parent, OrganisationRequest organisationRequest) {
        if (organisationRequest.getDaughters() == null) {
            organisations.add(
                    Organisation.builder()
                            .organisationId(
                                    OrganisationId.builder()
                                            .name(parent)
                                            .childName(organisationRequest.getName())
                                            .build())
                            .build());
        } else {

            organisationRequest.getDaughters().iterator().forEachRemaining(daughter -> {
                if (parent != null) {
                    organisations.add(
                            Organisation.builder()
                                    .organisationId(
                                            OrganisationId.builder()
                                                    .name(parent)
                                                    .childName(organisationRequest.getName())
                                                    .build())
                                    .build());
                }
                findAllOrganisations(organisations, organisationRequest.getName(), daughter);
            });
        }
    }
}
