package product.crud.usercrud.service;

import product.crud.usercrud.models.User;

import java.util.List;

public interface IUserService {

    long addUser(User user);

    User getUserById(long id);

    List<String> getGroupsByUsernameAndPassword(String username, String password);
}
