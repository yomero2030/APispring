package org.springframework.samples.petclinic;


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
 * @author AugustoRuCle
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test

public class VetControllerTest {

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
    
    private int idUser = 1;
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testFindVet() throws Exception {
        mvc.perform(get("/vets/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("vets/findVets"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testGetVetById() throws Exception {
        mvc.perform(get("/vets/2"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("vet"))
            .andExpect(view().name("vets/vetDetails"));
    }

    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testAddGetVet() throws Exception {
        mvc.perform(get("/vets/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("vets/createOrUpdateVetForm"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testEditGetVet() throws Exception {
        mvc.perform(get("/vets/"+idUser+"/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("vets/createOrUpdateVetForm"));
    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testEditPostVet() throws Exception {
        mvc.perform(post("/vets/"+idUser+"/edit")
            .param("firstName", "vet3")
            .param("lastName", "vet3")
            .param("specialty", "1234")    
            .param("telephone", "123")  
            .param("Schedule", "2:40")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/vets/"+idUser));
    } 
        
//    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
//    @Test
//    public void testDeletePostVet() throws Exception {
//        mvc.perform(get("/vets/"+idUser+"/delete"))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(view().name("redirect:/vets?lastName="));
//    }
    
    @WithMockUser(username = "admin@admin.com", authorities = { "ADMIN_PRIVILEGE" })
    @Test
    public void testAddtVet() throws Exception {
        mvc.perform(post("/vets/new")
            .param("firstName", "vet2")
            .param("lastName", "vet2")
            .param("specialty", "1234")    
            .param("telephone", "123")  
            .param("Schedule", "2:40")
        )
            .andExpect(status().is3xxRedirection());
    } 

     
}
