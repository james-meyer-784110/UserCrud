package product.crud.usercrud.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import product.crud.usercrud.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE users.name = :username AND users.password = :password",
            nativeQuery = true)
    User findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

}
