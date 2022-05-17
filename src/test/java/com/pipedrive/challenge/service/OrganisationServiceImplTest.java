package com.pipedrive.challenge.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.SliceImpl;
import com.pipedrive.challenge.entity.Organisation;
import com.pipedrive.challenge.entity.OrganisationId;
import com.pipedrive.challenge.model.OrganisationRequest;
import com.pipedrive.challenge.model.OrganisationResponse;
import com.pipedrive.challenge.model.RelationshipTypes;
import com.pipedrive.challenge.repository.OrganisationRepository;
import com.pipedrive.challenge.view.OrganisationRelationshipsView;

@ExtendWith(MockitoExtension.class)
class OrganisationServiceImplTest {

    @InjectMocks
    private OrganisationServiceImpl organisationService;

    @Mock
    private OrganisationRepository organisationRepository;

    @Captor
    ArgumentCaptor<Set<Organisation>> organisationSetCaptor;

    @Test
    void createOrganisation() {

        OrganisationRequest req = new OrganisationRequest("AB");
        req.setDaughters(List.of(new OrganisationRequest("CD"), new OrganisationRequest("EF")));

        when(organisationRepository.saveAll(organisationSetCaptor.capture()))
                .thenAnswer(i -> i.getArguments()[0]);

        organisationService.createOrganisation(req);

        assertTrue(organisationSetCaptor.getValue().containsAll(Set.of(
                Organisation.builder()
                        .organisationId(
                                OrganisationId.builder()
                                        .name("AB")
                                        .childName("EF")
                                        .build())
                        .build(),
                Organisation.builder()
                        .organisationId(
                                OrganisationId.builder()
                                        .name("AB")
                                        .childName("CD")
                                        .build())
                        .build())));
    }

    @Test
    void fetchAllOrganisationRelatioons() {
        OrganisationRelationshipsView projection = new OrganisationRelationshipsView() {
            @Override
            public String getName() {
                return "AB";
            }

            @Override
            public RelationshipTypes getRelationship() {
                return RelationshipTypes.parent;
            }
        };

        when(organisationRepository.findOrganisationRelationships(any(), any()))
                .thenReturn(new SliceImpl<>(List.of(projection)));

        Set<OrganisationResponse> actual = organisationService.fetchAllOrganisationRelatioons(anyString(), anyInt());

        assertTrue(actual.containsAll(List.of(new OrganisationResponse("AB", RelationshipTypes.parent))));
    }
}
