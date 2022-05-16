package com.pipedrive.challenge.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "organisation", schema = "public")
@AllArgsConstructor
@RequiredArgsConstructor
public class Organisation implements Serializable {

    @EmbeddedId
    private OrganisationId organisationId;

    @Transient
    private String relationship;
}