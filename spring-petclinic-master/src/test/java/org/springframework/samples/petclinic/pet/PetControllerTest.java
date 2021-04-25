/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.pet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author legad
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test

public class PetControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;
    
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    
    private int idPet = 1;
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testInitCreationForm() throws Exception {
        mvc.perform(get("/owner/pets"))
            .andExpect(status().isOk())
            .andExpect(view().name("owners/ownerDetailsOwner"));
    }
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testCreatePetPost() throws Exception {
        mvc.perform(post("/owner/pets/new")
            .param("name", "Gratus"+idPet)
            .param("birthDate", "2019-03-05")
            .param("type", "cat")
        )
            .andExpect(status().isOk());
    }
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testCreatePetGet() throws Exception {
        mvc.perform(get("/owner/pets/new"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetFormOwner"));
    }
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testUpdatePetGet() throws Exception {
        mvc.perform(get("/owner/pets/"+(idPet)+"/edit"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetFormOwner"));
    }
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testUpdatePetPost() throws Exception {
        mvc.perform(post("/owner/pets/"+(idPet)+"/edit")
            .param("name", "Gratus2")
            .param("birthDate", "2019-02-05")
            .param("type", "cat")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owner/pets/"));
    }
    
}
