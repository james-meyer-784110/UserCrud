package product.crud.usercrud.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import product.crud.usercrud.exceptions.*;
import product.crud.usercrud.models.User;
import product.crud.usercrud.models.UserEmailUpdate;
import product.crud.usercrud.models.UserPasswordUpdate;
import product.crud.usercrud.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor @AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User addUser(User user) throws PasswordLengthException {
        if(user.getPassword().length() < 8){
            throw new PasswordLengthException();
        }

        user.setPassword(this.hashPassword(user.getPassword()));
        user.setUserGroups(null);

        User result = userRepo.save(user);
        return result;
    }

    @Override
    public User updateUserPassword(UserPasswordUpdate update)
            throws NotFoundException, UnauthorizedException, PasswordLengthException
    {
        User user = userRepo.findByUsername(update.getUserName());
        if(user == null){
            throw new NotFoundException();
        }
        else if(!user.getPassword().equals(hashPassword(update.getOldPassword()))){
            throw new UnauthorizedException();
        }
        else if(user.getPassword().length() < 8){
            throw new PasswordLengthException();
        }

        user.setPassword(hashPassword(update.getNewPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUserEmail(UserEmailUpdate update)
            throws NotFoundException, UnauthorizedException
    {
        User user = userRepo.findByUsername(update.getUserName());
        if(user == null){
            throw new NotFoundException();
        }
        else if(!user.getPassword().equals(hashPassword(update.getPassword()))){
            throw new UnauthorizedException();
        }

        user.setEmail(update.getEmail());
        return userRepo.save(user);
    }

    @Override
    public User addUserGroupToUser(long id, String group) throws NotFoundException {
        Optional<User> queryResult = userRepo.findById(id);
        if(queryResult.isEmpty()){
            throw new NotFoundException();
        }

        User user = queryResult.get();
        user.addUserGroup(group);
        return userRepo.save(user);
    }

    @Override
    public User deleteGroupFromUser(long id, String group) throws NotFoundException {
        Optional<User> queryResult = userRepo.findById(id);
        if(queryResult.isEmpty()){
            throw new NotFoundException();
        }

        User user = queryResult.get();
        user.removeUserGroup(group);
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(long id) throws NotFoundException {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException();
        }

        User user = result.get();
        userRepo.delete(user);
        return user;
    }

    @Override
    public User getUserById(long id) throws NotFoundException {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()){
            throw new NotFoundException();
        }

        User user = result.get();
        return user;
    }

    @Override
    public List<String> getGroupsByUsernameAndPassword(String username, String password)
            throws NotFoundException, PasswordMismatchException
    {
        User result = userRepo.findByUsername(username);
        if(result == null){
            throw new NotFoundException();
        }
        else if(!result.getPassword().equals(hashPassword(password))){
            throw new PasswordMismatchException();
        }

        result = userRepo.findById(result.getId()).get();
        return result.getUserGroupsAsStrings();
    }

    private String hashPassword(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
