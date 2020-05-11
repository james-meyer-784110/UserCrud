package product.crud.usercrud.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Test
    public void sanityTest(){
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(userService);
    }

    @Test
    public void addUserReturnsUser() throws Exception {
        User user = new User(0, "alice", "alice@email.com", "password123", null);

        when(userRepository.save(user))
                .thenReturn(user);

        Assert.assertNotNull(userService.addUser(user));
    }

    @Test
    public void addUserReturnsUserWithEmptyPassword() throws Exception {
        User user = new User(0, "alice", "alice@email.com", "password123", null);

        when(userRepository.save(user))
                .thenReturn(user);

        Assert.assertEquals("", userService.addUser(user).getPassword());
    }

    @Test
    public void getUserByIdReturnsNullIfOptionalIsEmpty() throws NotFoundException {
        when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertNull(userService.getUserById(1L));
    }

    @Test
    public void getUserByIdReturnsUserWithEmptyPasswordWhenOptionalNotEmpty() throws NotFoundException {
        User user = new User(0, "bob", "bob@email.com", "password123", null);

        when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1);
        Assert.assertNotNull(result);
        Assert.assertEquals("", result.getPassword());
    }
}
