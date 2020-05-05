package product.crud.usercrud.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private IUserService userService;

    private UserRepository userRepository;

    @BeforeAll
    public void init(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void sanityTest(){
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(userService);
    }

    @Test
    public void addUserReturnsUser(){
        User user = new User(0, "alice", "alice@email.com", "password123", null);

        when(userRepository.save(user))
                .thenReturn(user);

        Assert.assertNotNull(userService.addUser(user));
    }

    @Test
    public void addUserReturnsUserWithEmptyPassword(){
        User user = new User(0, "alice", "alice@email.com", "password123", null);

        when(userRepository.save(user))
                .thenReturn(user);

        Assert.assertEquals("", userService.addUser(user).getPassword());
    }

    @Test
    public void getUserByIdReturnsNullIfOptionalIsEmpty(){
        when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertNull(userService.getUserById(1));
    }

    @Test
    public void getUserByIdReturnsUserWithEmptyPasswordWhenOptionalNotEmpty(){
        User user = new User(0, "bob", "bob@email.com", "password123", null);

        when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1);
        Assert.assertNotNull(result);
        Assert.assertEquals("", result.getPassword());
    }
}
