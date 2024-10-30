package sql.security;

import org.springframework.security.core.GrantedAuthority;

import javax.management.relation.Role;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

public class RoleEntity implements GrantedAuthority {

    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
    public RoleEntity(){
    }

    public RoleEntity(Long id) {
        this.id = id;
    }

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
