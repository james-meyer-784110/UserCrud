package product.crud.usercrud.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import product.crud.usercrud.repo.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceIT {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @After
    public void after(){
        userRepo.deleteAll();
    }

    @Test
    public void sanityTest(){
        Assert.assertNotNull(userRepo);
        Assert.assertNotNull(userService);
    }
    
}
