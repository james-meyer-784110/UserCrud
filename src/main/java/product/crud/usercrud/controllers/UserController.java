package product.crud.usercrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import product.crud.usercrud.models.User;
import product.crud.usercrud.repo.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping(path="/add")
    public @ResponseBody String addUser(@RequestBody User user){
        userRepo.save(user);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }
}
