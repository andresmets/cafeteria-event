package ee.andres.cafeteria.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authorities extends CommonEntity{
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;
}
