package com.pipedrive.challenge.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.pipedrive.challenge.entity.Organisation;
import com.pipedrive.challenge.view.OrganisationRelationshipsView;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisation, Long> {

    int PAGE_SIZE = 100;

    @Query(value = "select child_name as name, 'daughter' as relationship from organisation where name = :name " +
            "union " +
            "select name as name, 'parent' as relationship from organisation where child_name = :name " +
            "union " +
            "select distinct child_name as name, 'sister' as relationship from organisation where " +
            "name in (select name from organisation where child_name = :name ) " +
            "and child_name <> :name " +
            "order by name", nativeQuery = true)
    Slice<OrganisationRelationshipsView> findOrganisationRelationships(@Param("name") String organisationName, Pageable pageable);
}
