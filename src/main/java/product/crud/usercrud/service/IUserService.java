package product.crud.usercrud.service;

import product.crud.usercrud.exceptions.*;
import product.crud.usercrud.models.User;
import product.crud.usercrud.models.UserEmailUpdate;
import product.crud.usercrud.models.UserPasswordUpdate;

import java.util.List;

public interface IUserService {

    User addUser(User user)
            throws PasswordLengthException, WebAppException;

    User updateUserPassword(UserPasswordUpdate update)
            throws NotFoundException, UnauthorizedException, PasswordLengthException;

    User updateUserEmail(UserEmailUpdate update)
            throws NotFoundException, UnauthorizedException;

    User addUserGroupToUser(long id, String group)
            throws NotFoundException;

    User deleteGroupFromUser(long id, String group)
            throws NotFoundException;

    User deleteUser(long id) throws NotFoundException;

    User getUserById(long id) throws NotFoundException;

    User getUserByName(String name) throws NotFoundException;

    List<String> getGroupsByUsernameAndPassword(String username, String password)
            throws NotFoundException, PasswordMismatchException;
}
