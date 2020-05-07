package product.crud.usercrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.WebAppException;
import product.crud.usercrud.models.UserGroup;
import product.crud.usercrud.service.IUserGroupService;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    IUserGroupService userGroupService;

    @PostMapping(path="/add")
    public ResponseEntity<?> addUserGroup(@RequestBody String group){
        try {
            UserGroup result = userGroupService.addUserGroup(group);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (WebAppException e){
            return new ResponseEntity<>(
                    String.format("UserGroup %s already exists", group),
                    HttpStatus.CONFLICT
            );
        }
    }

    @PostMapping(path="/delete")
    public ResponseEntity<?> deleteUserGroup(@RequestBody String group){
        try{
            UserGroup result = userGroupService.deleteUserGroup(group);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(
                    String.format("UserGroup %s not found", group),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
