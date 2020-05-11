package product.crud.usercrud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import product.crud.usercrud.models.User;
import product.crud.usercrud.service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @Before
    public void init(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void sanityTest(){
        Assert.assertNotNull(mockMvc);
        Assert.assertNotNull(objectMapper);
        Assert.assertNotNull(userService);
    }

    @Test
    public void addUserReturnsOkOnValidUser() throws Exception {
        User user = new User(0, "alice", "alice@email.com", "password123", null);
        String json = objectMapper.writeValueAsString(user);

        when(userService.addUser(Mockito.any(User.class)))
                .thenReturn(user);

        mockMvc.perform(
                post("/user/add")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    
}
