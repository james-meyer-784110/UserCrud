package product.crud.usercrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.crud.usercrud.exceptions.NotFoundException;
import product.crud.usercrud.exceptions.WebAppException;
import product.crud.usercrud.models.UserGroup;
import product.crud.usercrud.repo.UserGroupRepository;

@Service
public class UserGroupService implements IUserGroupService {

    @Autowired
    private UserGroupRepository groupRepository;

    @Override
    public UserGroup addUserGroup(String group) throws WebAppException {
        try {
            UserGroup userGroup = new UserGroup();
            userGroup.setName(group);
            return groupRepository.save(userGroup);
        }
        catch (Exception e){
            throw new WebAppException();
        }
    }

    @Override
    public UserGroup deleteUserGroup(String group) throws NotFoundException {
        UserGroup result = groupRepository.findByName(group);
        if(result == null){
            throw new NotFoundException();
        }

        groupRepository.delete(result);
        return result;
    }
}
