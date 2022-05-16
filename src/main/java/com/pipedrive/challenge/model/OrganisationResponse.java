package com.pipedrive.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganisationResponse {

    @JsonProperty(value = "org_name")
    private String name;

    @JsonProperty(value = "relationship_type")
    private RelationshipTypes relationshipType;
}
