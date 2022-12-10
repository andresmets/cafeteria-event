package ee.andres.cafeteria.dao;

import ee.andres.cafeteria.pojo.User;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.security.Principal;

@Repository
public class UserDao extends AbstractDao<User> {
    public User findByLoggedInUser(Principal principal) {
        TypedQuery<User> q = getEntityManager().createNamedQuery("userByPrincipal", User.class);
        q.setParameter("username", principal.getName());
        return q.getSingleResult();
    }
}
