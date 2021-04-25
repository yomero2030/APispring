/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.visit;

import org.junit.Before;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author legad
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class VisitControllerTests {
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
    private int idPet = 3;
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testInitCreationForm() throws Exception {
        mvc.perform(get("/admin/owners/*/pets/"+idPet+"/visits/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"}) //owner
    @Test
    public void testOwnerCitasPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        mvc.perform(MockMvcRequestBuilders.multipart("/admin/owners/*/pets/"+idPet+"/visits/new").file(file)
                .param("visit_date", "2019-03-30")
                .param("description", "ver al medico")
                .param("pet_id", "3")
                
        //.with(csrf())
        )
                .andExpect(status().is3xxRedirection());
                //.andExpect(view().name("/admin/owners/{ownerId}"));
    }
    
}
