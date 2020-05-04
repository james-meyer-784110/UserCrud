package product.crud.usercrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public long addUser(User user) {
        user.setPassword(this.hashPassword(user.getPassword()));
        userRepo.save(user);
        return userRepo.count();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> result = userRepo.findById(id);
        if(result.isEmpty()){
            return null;
        }

        User user = result.get();
        user.setPassword("");
        return user;
    }

    @Override
    public List<String> getGroupsByUsernameAndPassword(String username, String password) {
//        User result = userRepo.findByUsernameAndPassword(username, hashPassword(password));
//        if(result == null){
//            return new ArrayList<>(0);
//        }
//
//        return result.getUserGroupsAsStrings();
        return null;
    }

    private String hashPassword(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
