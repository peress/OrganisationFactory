package com.pipedrive.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganisationResponse {

    private String name;
    private RelationshipTypes relationshipType;
}
