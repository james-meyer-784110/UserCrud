package product.crud.usercrud.repo;

import org.springframework.data.repository.CrudRepository;
import product.crud.usercrud.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
