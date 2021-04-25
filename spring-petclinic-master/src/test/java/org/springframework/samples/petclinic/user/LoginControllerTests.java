/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.user;

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
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 *
 * @author katZ_
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class LoginControllerTests {

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
    
    @Test
    public void testInitCreationForm() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/login"));
    }

    @Test
    public void testLoginFormAdmin() throws Exception {
        mvc.perform(post("/login")
                .param("username", "admin@admin.com")
                .param("password", "admin")
        )
                .andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void testLoginFormOwner() throws Exception {
        mvc.perform(post("/login")
                .param("username", "owner@owner.com")
                .param("password", "owner")
        )
                .andExpect(status().is3xxRedirection());
    }
    
    @Test
    public void testLoginFormDisabled() throws Exception {
        mvc.perform(post("/login")
                .param("username", "prueba@prueba2.com")
                .param("password", "prueba")
        )
                .andExpect(redirectedUrl("/login?disabled"));        
    }
    
    @Test
    public void testLoginFormError() throws Exception {
        mvc.perform(post("/login")
                .param("username", "test")
                .param("password", "lol")
        )
                .andExpect(redirectedUrl("/login?error"));        
    }
}
