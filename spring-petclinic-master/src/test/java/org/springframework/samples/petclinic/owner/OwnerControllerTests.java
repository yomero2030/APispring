/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

/**
 *
 * @author katZ_
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class OwnerControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;
    
    int OWNER_ID = 3;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testOwnerSignUpGet() throws Exception {
        mvc.perform(get("/owner_signup"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("owners/createOrUpdateOwnerFormInicial"));
    }

    //Para Correr, primero borra el record ingresado en la bd y entonces ya podras descomentar esto
//    @Test
//    public void testOwnerSignUpPost() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);
//        
//        mvc.perform(MockMvcRequestBuilders.multipart("/owner_signup").file(file)
//                .param("owner.firstName", "Borrar")
//                .param("owner.lastName", "ownersito")
//                .param("owner.address", "Calle Glorioso Lodasal")
//                .param("owner.city", "Tuxtlas")
//                .param("owner.telephone", "123456")
//                .param("owner.user.email", "borrar@porfavor.com")
//                .param("owner.user.password", "")                
//                .param("owner.longitud", "1")
//                .param("owner.latitud", "1")
//                .param("name", "Borrar") 
//                .param("birthDate", "2000-09-07") 
//                .param("type", "bird")                 
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/login"));
//    }
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test
    public void testAdminFindOwnersGet() throws Exception {
        mvc.perform(get("/admin/owners/find"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test
    public void testAdminOwnersGet() throws Exception {
        mvc.perform(get("/admin/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("owners/ownersList"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test
    public void testUpdateOwnerGet() throws Exception {
        mvc.perform(get("/admin/owners/{ownerId}/edit", OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test
    public void testUpdateOwnerPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);
        
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/owners/{ownerId}/edit", OWNER_ID).file(file)
                .param("firstName", "Joe unitario")
                .param("lastName", "Franklin")
                .param("address", "110 W. Liberty St.")
                .param("city", "Madison")
                .param("telephone", "65551023")
                .param("user.email", "prueba@prueba2.com")
                .param("user.password", "owner")
                .param("longitud", "1")
                .param("latitud", "1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/owners/{ownerId}"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test(expected = NestedServletException.class) //nullpointer porque no debe existir -1
    public void testUpdateOwnerPostFail() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);
        
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/owners/{ownerId}/edit", -1).file(file)//-1, siempre debe dar error
                .param("firstName", "Joe Unitario aka-")
                .param("lastName", "-Esto no deberia funcionar")
                .param("address", "110 W. Liberty St.")
                .param("city", "Madison")
                .param("telephone", "6085551023")
                .param("user.email", "fon@fon.com")
                .param("user.password", "")
                .param("longitud", "1")
                .param("latitud", "1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/owners/{ownerId}"));
                //Redirige ahi mismo pero con un error: Model object must not be null
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" }) //admin
    @Test
    public void testViewOwnerGet() throws Exception {
        mvc.perform(get("/admin/owners/{ownerId}", OWNER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/ownerDetails"));
    }
    
    @WithMockUser(username = "owner@owner.com", authorities = {"OWNER_PRIVILEGE"}) //owner
    @Test
    public void testMyProfileGet() throws Exception {
        mvc.perform(get("/owner/my_profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @WithMockUser(username = "owner@owner.com", authorities = {"OWNER_PRIVILEGE"}) //owner
    @Test
    public void testMyProfilePost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        mvc.perform(MockMvcRequestBuilders.multipart("/owner/my_profile").file(file)
                .param("firstName", "Don owner")
                .param("lastName", "ownersito")
                .param("address", "Calle Glorioso Lodasal")
                .param("city", "Tuxtlas")
                .param("telephone", "123456")
                .param("user.email", "owner@owner.com")
                .param("user.password", "")
                .param("longitud", "1")
                .param("latitud", "1")
        //.with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owner/my_profile"));
    }

    @WithMockUser(username = "test", authorities = {"ADMIN_PRIVILEGE"}) //owner
    @Test
    public void testListOwnersGet() throws Exception {
        mvc.perform(get("/admin/user/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list_owner"));
    }
}
