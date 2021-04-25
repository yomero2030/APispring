/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.medicamentos;

import org.junit.Before;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MedicamentoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getMedicamentos() throws Exception {
        this.mockMvc.perform(get("/medicamento"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("medicamentos/medicamentosList"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getMedicamentosFind() throws Exception {
        this.mockMvc.perform(get("/medicamento/find"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("medicamento"))
                .andExpect(view().name("medicamentos/findMedicamentos"));
    }

    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getProductsReport() throws Exception {
        this.mockMvc.perform(get("/medicamento2"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("medicamentos/medicamentosList2"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void initNewProduct() throws Exception {
        this.mockMvc.perform(get("/medicamento/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("medicamento"))
                .andExpect(view().name("medicamentos/createOrUpdateMedicamentoForm"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"}) //owner
    @Test
    public void postNewProduct() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/medicamento/new")
                .param("ingrediente_activo", "ingrediente aaaaactivo")
                .param("nombre", "nombreeeeee")
                .param("presentacion", "presentaciooooon")
        );
        //.andExpect(status().is3xxRedirection());
    }

    
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getUpdateMedicamento() throws Exception {
        this.mockMvc.perform(get("/medicamento/{medicamentoId}/edit", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("medicamento"));
         //  .andExpect(status().is3xxRedirection());
    }
    
    
    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void postUpdateMedicamento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/medicamento/{medicamentoId}/edit", 1)
            .param("nombre", "paracetamol 2.0")
            .param("ingrediente_activo", "paracetamol")
        )
            .andExpect(status().isOk())
//            .andExpect(model().attributeHasErrors("medicamento"))
  //          .andExpect(model().attributeHasFieldErrors("medicamento", "presentacion"))
            .andExpect(view().name("medicamentos/createOrUpdateMedicamentoForm"));
    }
    
    
    
    
}
