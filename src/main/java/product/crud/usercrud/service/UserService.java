package product.crud.usercrud.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.PasswordMismatchException;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor @AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User addUser(User user) throws Exception {
        user.setPassword(this.hashPassword(user.getPassword()));
        //user.setUserGroups(null);

        User result = userRepo.save(user);
        return result;
    }

    @Override
    public User deleteUser(long id){
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()){
            return null;
        }

        User user = result.get();
        userRepo.delete(user);
        return user;
    }

    @Override
    public User getUserById(long id) {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()){
            return null;
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
