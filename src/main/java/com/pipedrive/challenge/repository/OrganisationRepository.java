package com.pipedrive.challenge.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.pipedrive.challenge.entity.Organisation;

@Repository
public interface OrganisationRepository extends PagingAndSortingRepository<Organisation, Long> {

    int PAGE_SIZE = 100;

    Slice<Organisation> findByNameOrderByNameDesc(String organisationName, Pageable pageable);
}
