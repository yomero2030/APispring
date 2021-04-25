/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.citas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CitasControllerTest {
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
    
    @WithMockUser(username = "owner@owner.com", authorities = { "OWNER_PRIVILEGE" })
    @Test
    public void testInitCreationForm() throws Exception {
        mvc.perform(get("/owner/cita"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("citas"))
                .andExpect(view().name("citas/appointment"));
    }

    @WithMockUser(username = "owner@owner.com", authorities = {"OWNER_PRIVILEGE"}) //owner
    @Test
    public void testOwnerCitasPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        mvc.perform(MockMvcRequestBuilders.multipart("/owner/cita").file(file)
                .param("fecha", "2019-03-30")
                .param("hora", "22:22")
                .param("mascota", "3")
                .param("especialidad", "2")
                .param("confirmacion", "0")
        //.with(csrf())
        )
                .andExpect(status().is3xxRedirection());
                //.andExpect(view().name("redirect:/owner/cita"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testInitCreationFormAdmin() throws Exception {
        mvc.perform(get("/admin/citas"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("citas"))
                .andExpect(view().name("citas/citasadmin"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"}) //owner
    @Test
    public void testCitasAdminPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        mvc.perform(MockMvcRequestBuilders.multipart("/admin/citas").file(file)
                .param("fecha", "2019-03-30")
                .param("hora", "22:22")
                .param("mascota", "1")
                .param("especialidad", "2")
                .param("confirmacion", "0")
                .param("owner","2")
        //.with(csrf())
        )
                .andExpect(status().is3xxRedirection());
                //.andExpect(view().name("redirect:/owner/cita"));
    }
}
