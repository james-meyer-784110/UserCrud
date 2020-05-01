package product.crud.usercrud.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_groups",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "group_name")
})
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @Column(name = "group_name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "userGroups")
    private List<User> users;

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
