/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

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
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;
    
    int PRODUCT_ID = 1;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getProducts() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(view().name("producto/product"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void getProductsReport() throws Exception {
        this.mockMvc.perform(get("/products/report"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(view().name("producto/product-report"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void initNewProduct() throws Exception {
        this.mockMvc.perform(get("/product/create-product"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("producto/product-create"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"}) //owner
    @Test
    public void postNewProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/product/create-product").file(file)
                .param("name", "Collar de gatos")
                .param("description", "Collarcitossss")
                .param("price", "1000000.12")
                .param("existence", "12")
                .param("photo", "/resources/images/default-image.jpg")
        );
        //.andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"}) //admin
    @Test
    public void getUpdateProduct() throws Exception {
        this.mockMvc.perform(get("/product/edit/{productId}", PRODUCT_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("producto/product-edit"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
    @Test
    public void updateProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[1]);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/product/edit/{productId}", PRODUCT_ID).file(file)
                .param("name", "nuevo Collar de gatos")
                .param("description", "otro Collarcitossss")
                .param("price", "1000000.12")
                .param("existence", "12")
                .param("photo", "/resources/images/default-image.jpg")
        );
        //.andExpect(status().is3xxRedirection())
    }

    //Necesita previamente un producto, funciona pero necesita un producto 1
//    @WithMockUser(username = "admin@admin.com", authorities = {"ADMIN_PRIVILEGE"})
//    @Test
//    public void deleteProduct() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/1"))
//                //EN CASO DE QUE FALLE ES POR QUE NO HAY UN PRODUCTO EN LA DB CON EL ID=3 :(
//
//                .andExpect(status().isOk());
//        //  .andExpect(view().name("producto/product"));
//
//    }
}
