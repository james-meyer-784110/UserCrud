package product.crud.usercrud;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import product.crud.usercrud.controllers.UserController;

@SpringBootTest
class UsercrudApplicationTests {

    @Autowired
    private UserController userController;

	@Test
	void contextLoads() {
        Assert.assertNotNull(userController);
	}

}
