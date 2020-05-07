package product.crud.usercrud.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.PasswordLengthException;
import product.crud.usercrud.exceptions.PasswordMismatchException;
import product.crud.usercrud.models.User;
import product.crud.usercrud.models.UserEmailUpdate;
import product.crud.usercrud.models.UserPasswordUpdate;
import product.crud.usercrud.service.IUserService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping(path="/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            User result = userService.addUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (PasswordLengthException e){
            return new ResponseEntity<>("Password must be 8 characters or longer", HttpStatus.BAD_REQUEST);
        }
        // TODO: need to specify the constraint violation exception that occurs here
        catch (Exception e){
            return new ResponseEntity<>("Username or email already taken", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(path="/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserPasswordUpdate update){
        return null;
    }

    public ResponseEntity<?> updateEmail(@RequestBody UserEmailUpdate update){
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        try {
            User result = userService.getUserById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable long id){
        try {
            User result = userService.deleteUser(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            List<String> groups = userService.getGroupsByUsernameAndPassword(user.getName(), user.getPassword());

            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 60 * 2)))
                    .withClaim("groups", groups)
                    .sign(algorithm);

            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(
                    String.format("User %s does not exist", user.getName()),
                    HttpStatus.NOT_FOUND
            );
        }
        catch (PasswordMismatchException e){
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
