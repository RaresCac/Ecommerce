package rares.web.ecommece;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser" , roles = "USER")
    public void testAdminProductsGetWithUserLoggedIn() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "USER")
    public void testAdminOrdersGetWithUserLoggedIn() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminProductsWithAdminLoggedIn() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(model().attributeExists("productDTO"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminProductsPostWithAdmin() throws Exception {
        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "product")
                .param("description", "a product")
                .param("price", "10.5"))
                .andExpect(model().attributeExists("success"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "USER")
    public void testAdminProductsPostWithUser() throws Exception {
        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "product")
                .param("description", "a product")
                .param("price", "10.5"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminProductsPostWrongNameWithAdmin() throws Exception {
        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "")
                .param("description", "a product")
                .param("price", "10.5"))
                .andExpect(model().attributeHasFieldErrors("productDTO", "name"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminProductsPostWrongDescWithAdmin() throws Exception {
        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "product")
                .param("description", "")
                .param("price", "10.5"))
                .andExpect(model().attributeHasFieldErrors("productDTO", "description"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminProductsPostWrongPriceWithAdmin() throws Exception {
        mockMvc.perform(post("/admin/products")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "product")
                .param("description", "a product")
                .param("price", "-1"))
                .andExpect(model().attributeHasFieldErrors("productDTO", "price"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "testUser" , roles = "ADMIN")
    public void testAdminOrdersWithAdminLoggedIn() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(model().attributeDoesNotExist("cart"))   //Should not exist as test does not add to it beforehand
                .andExpect(status().isOk());
    }
}
