package rares.web.ecommece;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterGet() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterPostCorrectly() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "user")
                .param("email", "user@user.com")
                .param("password", "goodpassword")
                .param("matchingPassword", "goodpassword")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"));
    }

    @Test
    public void testRegisterUsernameNotValid() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "u")
                .param("email", "user@user.com")
                .param("password", "goodpassword")
                .param("matchingPassword", "goodpassword")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "username"));
    }

    @Test
    public void testRegisterEmailNotValid() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "user")
                .param("email", "user")
                .param("password", "goodpassword")
                .param("matchingPassword", "goodpassword")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "email"));
    }

    @Test
    public void testRegisterPasswordNotValid() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "user")
                .param("email", "user")
                .param("password", "pass")
                .param("matchingPassword", "pass")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "password"));
    }

    @Test
    public void testRegisterPasswordsDoNotMatch() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "user")
                .param("email", "user@user.com")
                .param("password", "goodpassword")
                .param("matchingPassword", "goodpassworddiff")
                .param("role", "USER"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("errorMessage", "User exists with the same username or email address"));
    }

    @Test
    public void testRegisterWrongRole() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.TEXT_PLAIN)
                .param("username", "user")
                .param("email", "user@user.com")
                .param("password", "password123")
                .param("matchingPassword", "password123")
                .param("role", "oddrole"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("userDTO", "role"));
    }
}
