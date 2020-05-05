package product.crud.usercrud.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.models.User;
import product.crud.usercrud.models.UserGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void sanityTest(){
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void addUserReturnsUser(){
        User user = new User(0, "alice", "alice@email.com", "password123", null);

        User result = userRepository.save(user);

        Assert.assertEquals(user, result);
    }

    @Test
    public void addUserAddsUserGroups() {
        User user = new User(0, "bob", "bob@email.com", "password123", null);
        List<User> users = new ArrayList<>(Arrays.asList(user));
        List<UserGroup> userGroups = new ArrayList<>(Arrays.asList(
                new UserGroup(0, "admins", users),
                new UserGroup(1, "authors", users)
        ));

        User result = userRepository.save(user);

        Assert.assertNotNull(result.getUserGroups());
        Assert.assertEquals("admins", result.getUserGroups().get(0));
        Assert.assertEquals("authors", result.getUserGroups().get(1));
    }
}
