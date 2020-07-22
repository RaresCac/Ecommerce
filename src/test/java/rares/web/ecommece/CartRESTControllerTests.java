package rares.web.ecommece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import rares.web.ecommece.entities.Product;
import rares.web.ecommece.repository.ProductRepository;
import rares.web.ecommece.service.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartRESTControllerTests {

    private final String ERROR_QUANTITY_INPUT = "Please enter a valid number between 0 and 10";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveTest() {
        //Test saving in repository and set up the test environment
        Product product = new Product();
        product.setId(1);
        product.setPrice(20);
        product.setName("A product");
        product.setDescription("This is a product");
        productRepository.save(product);
    }

    @Test
    public void testAddToCartSuccess() throws Exception {
        mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "1")
                .param("quantity", "1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void testAddToCartFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "-1")
                .param("quantity", "1"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        //Error message created in the controller, after bindingresult has errors
        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testAddToCartWrongFormatQuantity() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "1")
                .param("quantity", "test"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testAddToCartWrongFormatID() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "wrong")
                .param("quantity", "2"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }

    @Test
    public void testAddToCartQuantityUpperBounds() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "1")
                .param("quantity", "11"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testAddToCartQuantityLowerBounds() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/add")
                .contentType(MediaType.TEXT_PLAIN)
                .param("id", "1")
                .param("quantity", "0"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testRemoveFromCartSuccess() throws Exception {
        mockMvc.perform(post("/cart/remove")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testRemoveFromCartFail() throws Exception {
        mockMvc.perform(post("/cart/remove")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFromCartSuccess() throws Exception {
        mockMvc.perform(post("/cart/remove")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "1")
                .param("quantity", "5"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testUpdateFromCartExceptionFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/update")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "1")
                .param("quantity", "test"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateFromCartUpperBoundFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/update")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "1")
                .param("quantity", "11"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateFromCartLowerBoundFail() throws Exception {
        MvcResult result = mockMvc.perform(post("/cart/update")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "1")
                .param("quantity", "0"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        Assertions.assertEquals(ERROR_QUANTITY_INPUT, result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateFromCartNoProductFail() throws Exception {
        mockMvc.perform(post("/cart/update")
                .contentType(MediaType.TEXT_PLAIN)
                .param("productId", "-1")
                .param("quantity", "5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
