package rares.web.ecommece;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCartGet() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(status().isOk());
    }


    @Test
    public void testCheckoutGet() throws Exception {
        mockMvc.perform(get("/cart/checkout"))
                .andExpect(model().attributeExists("checkoutDTO"))
                .andExpect(status().isOk());
    }


    @Test
    public void testCartCheckoutPostCorrect() throws Exception {
        mockMvc.perform(post("/cart/checkout")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "test")
                .param("address", "test")
                .param("city", "test")
                .param("postcode", "test")
                .param("country", "test")
                .param("state", "test")
                .param("email", "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart?success"));
    }

    @Test
    public void testCartCheckoutPostWrong() throws Exception {
        mockMvc.perform(post("/cart/checkout")
                .contentType(MediaType.TEXT_PLAIN)
                .param("name", "")
                .param("address", "")
                .param("city", "")
                .param("postcode", "")
                .param("country", "")
                .param("state", "")
                .param("email", ""))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "name"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "address"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "city"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "postcode"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "country"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "state"))
                .andExpect(model().attributeHasFieldErrors("checkoutDTO", "email"))
                .andExpect(view().name("checkout"))
                .andExpect(status().isOk());
    }
}
