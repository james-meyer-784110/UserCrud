package product.crud.usercrud.service;

import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.WebAppException;
import product.crud.usercrud.models.UserGroup;

public interface IUserGroupService {

    UserGroup addUserGroup(String group) throws WebAppException;

    UserGroup deleteUserGroup(String group) throws NotFoundException;

}
