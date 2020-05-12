package product.crud.usercrud.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.WebAppException;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceIT {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IUserService userService;

    private User alice;
    private User bob;

    @Before
    public void before() {
        alice = new User(0, "alice", "alice@email.com", "password123", null);
        bob = new User(0, "bob", "bob@email.com", "password321", null);
    }

    @After
    public void after() {
        userRepo.deleteAll();
    }

    @Test
    public void sanityTest() {
        Assert.assertNotNull(userRepo);
        Assert.assertNotNull(userService);
    }

    @Test
    public void addUserThrowsExceptionWhenUniqueViolation() throws Exception {

        userService.addUser(alice);
        boolean throwsException = false;
        try {
            bob.setName("alice");
            userService.addUser(bob);
        } catch (WebAppException e) {
            throwsException = true;
        }
        Assert.assertTrue(throwsException);
    }

    @Test
    public void addUserReturnsUser() throws Exception {
        //User user = new User(0, "alice", "alice@email.com", "password123", null);
        // userRepo.save(user)
        Assert.assertEquals(alice.getName()
                , userService.addUser(alice).getName());
    }

    @Test
    public void getUserByIdThrowsNotFoundWhenEmpty() {
        boolean throwsNotEmpty = false;
        try {
            userService.getUserById(1L);
        }
        catch (NotFoundException e){
            throwsNotEmpty = true;
        }
        Assert.assertTrue(throwsNotEmpty);
    }

    @Test
    public void getUserByIdReturnsUserWithEmptyPasswordWhenOptionalNotEmpty() throws NotFoundException {
        userRepo.save(bob);

        User result = userService.getUserByName(bob.getName());
        Assert.assertNotNull(result);
        Assert.assertEquals(bob.getName(), result.getName());
        Assert.assertEquals(bob.getEmail(), result.getEmail());
    }

}