package product.crud.usercrud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.PasswordLengthException;
import product.crud.usercrud.exceptions.PasswordMismatchException;
import product.crud.usercrud.exceptions.UnauthorizedException;
import product.crud.usercrud.models.User;
import product.crud.usercrud.models.UserEmailUpdate;
import product.crud.usercrud.models.UserPasswordUpdate;
import product.crud.usercrud.service.UserService;

import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    public void addUserReturnsBadRequestOnShortPassword() throws Exception {
        User user = new User(0, "alice", "alice@email.com", "pa$$wrd", null);
        String json = objectMapper.writeValueAsString(user);

        when(userService.addUser(Mockito.any(User.class)))
                .thenThrow(PasswordLengthException.class);

        mockMvc.perform(
                post("/user/add")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

//  TODO: The codebase will need some kind of explicit exception identifier.
//  TODO: the service layer might need to be refactored to throw another exception from IUserService
//
//    @Test
//    public void addUserReturnsConflictOnDataCollision() throws Exception {
//        User user = new User(0, "alice", "alice@email.com", "password123", null);
//        String json = objectMapper.writeValueAsString(user);
//
//        when(userService.addUser(Mockito.any(User.class)))
//                .thenThrow(Exception.class);
//
//        mockMvc.perform(
//                post("/user/add")
//                        .content(json)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isConflict());
//    }

    @Test
    public void updatePasswordReturnsOKOnValidInput() throws Exception {
        UserPasswordUpdate update = new UserPasswordUpdate();
        update.setUserName("alice");
        update.setOldPassword("password123");
        update.setNewPassword("password123");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserPassword(Mockito.any(UserPasswordUpdate.class)))
                .thenReturn(new User());

        mockMvc.perform(
                post("/user/update-password")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updatePasswordReturnsNotFoundWhenUserNotExists() throws Exception {
        UserPasswordUpdate update = new UserPasswordUpdate();
        update.setUserName("bob");
        update.setOldPassword("hello_w0rld");
        update.setNewPassword("hell0_world");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserPassword(Mockito.any(UserPasswordUpdate.class)))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(
                post("/user/update-password")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void updatePasswordReturnsUnauthorizedWhenOldPasswordInvalid() throws Exception {
        UserPasswordUpdate update = new UserPasswordUpdate();
        update.setUserName("bob");
        update.setOldPassword("hello_w0rld");
        update.setNewPassword("hell0_world");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserPassword(Mockito.any(UserPasswordUpdate.class)))
                .thenThrow(UnauthorizedException.class);

        mockMvc.perform(
                post("/user/update-password")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void updatePasswordReturnsBadRequestWhenPasswordIsTooShort() throws Exception {
        UserPasswordUpdate update = new UserPasswordUpdate();
        update.setUserName("bob");
        update.setOldPassword("hello_w0rld");
        update.setNewPassword("hell0123");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserPassword(Mockito.any(UserPasswordUpdate.class)))
                .thenThrow(PasswordLengthException.class);

        mockMvc.perform(
                post("/user/update-password")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Password must be 8 characters or longer"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateEmailReturnsOkWhenUserExists() throws Exception {
        UserEmailUpdate update = new UserEmailUpdate();
        update.setUserName("bob");
        update.setEmail("bob@email.com");
        update.setPassword("password123");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserEmail(update))
                .thenReturn(new User());

        mockMvc.perform(
                post("/user/update-email")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmailReturnsNotFoundWhenUserNotExists() throws Exception {
        UserEmailUpdate update = new UserEmailUpdate();
        update.setUserName("bob");
        update.setEmail("bob@email.com");
        update.setPassword("password123");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserEmail(update))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(
                post("/user/update-email")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("No user with username bob exists"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmailReturnsUnauthorizedWhenPasswordNotMatch() throws Exception {
        UserEmailUpdate update = new UserEmailUpdate();
        update.setUserName("bob");
        update.setEmail("bob@email.com");
        update.setPassword("password123");
        String json = objectMapper.writeValueAsString(update);

        when(userService.updateUserEmail(update))
                .thenThrow(UnauthorizedException.class);

        mockMvc.perform(
                post("/user/update-email")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Invalid username or password"))
                .andExpect(status().isUnauthorized());
    }
}
