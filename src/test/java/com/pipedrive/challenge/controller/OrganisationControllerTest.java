package com.pipedrive.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.pipedrive.challenge.model.OrganisationResponse;
import com.pipedrive.challenge.service.OrganisationServiceImpl;

@WebMvcTest
class OrganisationControllerTest {

    @MockBean
    private OrganisationServiceImpl organisationService;

    @Autowired
    private MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> organisationNameCaptor;
    @Captor
    ArgumentCaptor<Integer> pageNumberCaptor;

    private static final String ORGANISATION_NAME = "Laranjas e Bananas";
    private static final int PAGE_NUMBER = 0;

    @Test
    void createOrganisation() {

        try {

            mockMvc.perform(post("/v1/organisations")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(Files.readAllBytes(Paths.get("src/test/resources/payload_create_organisation.json")))
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            fail();
        }
    }
/*
    @Test
    void createEmptyOrganisation() {
    //TODO dont allow organisations whitout name
    //TODO allow organisation whitout children
        try {

            mockMvc.perform(post("/v1/organisations")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{}")
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        } catch (Exception e) {
            fail();
        }
    }*/

    @Test
    void organisationRelations() {

        try {
            when(organisationService.fetchAllOrganisationRelatioons(organisationNameCaptor.capture(), pageNumberCaptor.capture()))
                    .thenReturn(Set.of(new OrganisationResponse()));

            MvcResult mvcResult = mockMvc.perform(get("/v1/organisations/relation/{organisationName}", ORGANISATION_NAME)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .param("pageNumber", String.valueOf(PAGE_NUMBER))
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            assertEquals(ORGANISATION_NAME, organisationNameCaptor.getValue());
            assertEquals(PAGE_NUMBER, pageNumberCaptor.getValue().intValue());
        } catch (Exception e) {
            fail();
        }
    }
}
