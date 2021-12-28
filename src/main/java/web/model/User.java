package web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="user")
public class User implements UserDetails {

    @ManyToMany(cascade = {MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_And_Roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleSet = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Byte age;

    @Column(name = "email")
    private String email;


    @Column(name = "password")
    private String password;

    @Transient
    private String[] roleSetTemp;

    public User(String name, String lastName, Byte age, String email, String password, Set<Role> roleSet) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roleSet = roleSet;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getRole(){
        if (this.roleSet.size() == 2) return roleSet.toArray()[0].toString().toLowerCase()+ " " + roleSet.toArray()[1].toString().toLowerCase();
        else return roleSet.toArray()[0].toString().toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public void setRoleSetTemp(String[] roleSetTemp) {
        this.roleSetTemp = roleSetTemp;
    }

    public String[] getRoleSetTemp(){
        return roleSetTemp;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(roleSet, user.roleSet) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(age, user.age) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Arrays.equals(roleSetTemp, user.roleSetTemp);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(roleSet, name, lastName, age, email, password);
        result = 31 * result + Arrays.hashCode(roleSetTemp);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "roleSet=" + roleSet +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
