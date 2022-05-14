package com.pipedrive.challenge.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationRequest {

    @JsonProperty("org_name")
    private String name;
    private List<OrganisationRequest> daughters;
}
