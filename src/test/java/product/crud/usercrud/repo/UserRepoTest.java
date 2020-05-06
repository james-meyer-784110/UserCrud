package product.crud.usercrud.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.models.User;

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
}
