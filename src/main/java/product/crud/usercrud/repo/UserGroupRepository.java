package product.crud.usercrud.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import product.crud.usercrud.models.UserGroup;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    @Query(value = "SELECT * FROM user_groups WHERE user_groups.group_name = :name", nativeQuery = true)
    UserGroup findByName(@Param("name") String name);
}
