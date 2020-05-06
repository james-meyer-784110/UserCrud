package product.crud.usercrud.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import product.crud.usercrud.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    @Query(value = "SELECT * FROM users WHERE users.user_name = :username AND users.user_password = :password",
            nativeQuery = true)
    @Deprecated
    User findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    @Query(value = "SELECT * FROM users WHERE users.user_name = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

}
