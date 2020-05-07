package product.crud.usercrud.models;

import lombok.Data;

@Data
public class UserPasswordUpdate {

    private String userName;

    private String oldPassword;

    private String newPassword;

}
