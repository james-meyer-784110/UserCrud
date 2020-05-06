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
import product.crud.usercrud.service.IUserService;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
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
        catch (Exception e){
            return new ResponseEntity<>("Username or email already taken", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User result = userService.getUserById(id);
        if(result == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable long id){
        User result = userService.deleteUser(id);
        if(result == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
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
