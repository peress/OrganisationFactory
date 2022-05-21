package com.pipedrive.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.pipedrive.challenge.model.OrganisationRelationshipsProjection;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrganisationRepositoryTest extends DatabaseContainer {

    @Autowired
    private OrganisationRepository organisationRepository;

    @Test
    void assertOrganisationRelations() {

        List<OrganisationRelationshipsProjection> result;

        result = organisationRepository.findOrganisationRelationships(
                        "Primeira Filha da Mae",
                        PageRequest.of(0, OrganisationRepository.PAGE_SIZE))
                .getContent();

        assertEquals(2, result.size());
     /*   assertThat(Stream.of(new AbstractMap.SimpleEntry<>("Segunda Filha da mesma Mae", RelationshipTypes.sister),
                        new AbstractMap.SimpleEntry<>("Mae", RelationshipTypes.parent)).collect(Collectors.toSet() ),
                hasItems(result.stream()
                        .map(output -> new AbstractMap.SimpleEntry<>(output.getName(), output.getRelationship()))
                        .collect(Collectors.toSet())));

      */

        result = organisationRepository.findOrganisationRelationships(
                        "Segunda Filha da mesma Mae",
                        PageRequest.of(0, OrganisationRepository.PAGE_SIZE))
                .getContent();

        assertEquals(3, result.size());

        result = organisationRepository.findOrganisationRelationships(
                        "Neta",
                        PageRequest.of(0, OrganisationRepository.PAGE_SIZE))
                .getContent();

        assertEquals(1, result.size());

        result = organisationRepository.findOrganisationRelationships(
                        "Mae",
                        PageRequest.of(0, OrganisationRepository.PAGE_SIZE))
                .getContent();

        assertEquals(2, result.size());
    }
}
