package ee.andres.cafeteria.pojo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
@NamedQueries(@NamedQuery(name="userByPrincipal", query = "from User u join fetch u.seller where u.username = :username"))
public class User extends CommonEntity{
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToOne
    private Seller seller;
    @OneToMany
    @JoinColumn(name = "username", referencedColumnName = "username")
    private List<Authorities> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }
}
