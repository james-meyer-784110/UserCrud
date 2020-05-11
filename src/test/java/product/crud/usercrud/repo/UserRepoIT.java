package product.crud.usercrud.repo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.models.User;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRepoIT {

    @Autowired
    private UserRepository repo;

    @After
    public void after(){
        repo.deleteAll();
    }

    @Test
    public void findByUsernameReturnsUserWhenValid() {
        User alice = new User(0, "alice", "alice@email.com", "password123", null);

        repo.save(alice);

        User result = repo.findByUsername(alice.getName());
        Assert.assertNotNull(result);
    }

    @Test
    public void findByUsernameReturnsNullWhenNotExists(){
        Assert.assertNull(repo.findByUsername("alice"));
    }

    @Test
    public void usersAreUnique(){
        User alice = new User(0, "alice", "alice@email.com", "password123", null);
        User copy = new User(0, "alice", "alice@email.com", "password123", null);

        repo.save(alice);

        boolean throwsException = false;
        try {
            repo.save(copy);
        }
        catch (DataIntegrityViolationException e){
            System.out.printf("\n.\n.\nEXCEPTION: %s\n.\n.\n", e.getClass());
            throwsException = true;
        }
        Assert.assertTrue(throwsException);
    }

}
