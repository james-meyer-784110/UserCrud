package product.crud.usercrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import product.crud.usercrud.models.User;
import product.crud.usercrud.service.IUserService;

import java.net.http.HttpResponse;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(path="/add")
    public @ResponseBody long addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("{id}")
    public @ResponseBody User getUserById(@RequestParam long id){
        return userService.getUserById(id);
    }

//    @GetMapping(path="/all")
//    public @ResponseBody Iterable<User> getAllUsers(){
//
//    }
}
