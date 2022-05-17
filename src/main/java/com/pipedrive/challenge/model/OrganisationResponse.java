package com.pipedrive.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationResponse {

    @JsonProperty(value = "org_name")
    private String name;

    @JsonProperty(value = "relationship_type")
    private RelationshipTypes relationshipType;
}
