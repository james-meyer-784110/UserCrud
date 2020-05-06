package product.crud.usercrud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_groups",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "group_name")
})
@Data @NoArgsConstructor @AllArgsConstructor
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    @JsonIgnore
    private long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "userGroups")
    @JsonIgnore
    private List<User> users;
}
