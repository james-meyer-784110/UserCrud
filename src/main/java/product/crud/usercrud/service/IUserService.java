package product.crud.usercrud.service;

import org.hibernate.exception.ConstraintViolationException;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.PasswordMismatchException;
import product.crud.usercrud.models.User;

import java.util.List;

public interface IUserService {

    User addUser(User user) throws Exception;

    User deleteUser(long id);

    User getUserById(long id);

    List<String> getGroupsByUsernameAndPassword(String username, String password)
            throws NotFoundException, PasswordMismatchException;
}
