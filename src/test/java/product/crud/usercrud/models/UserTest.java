package product.crud.usercrud.models;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserTest {

    private User user;

    @Test
    public void getUserGroupsAsStringsReturnsListStringsWhenNotEmpty() {
        user = new User();
        List<User> users = new ArrayList<>(Arrays.asList(user));

        user.setUserGroups(new ArrayList<>(Arrays.asList(
                new UserGroup(0, "admins", users),
                new UserGroup(1, "users", users),
                new UserGroup(2, "analysts", users)
        )));

        List<String> groups = user.getUserGroupsAsStrings();

        Assert.assertEquals("admins", groups.get(0));
        Assert.assertEquals("users", groups.get(1));
        Assert.assertEquals("analysts", groups.get(2));
    }
}