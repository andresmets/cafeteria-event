package ee.andres.cafeteria.service.impl;

import ee.andres.cafeteria.dao.UserDao;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.pojo.User;
import ee.andres.cafeteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service("UserService")
public class UserServiceImpl extends AbstractServiceImpl<Seller> implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public Seller getByLoggedInUser(Principal loggedInUser) {
        User u = getUserDao().findByLoggedInUser(loggedInUser);
        if(u != null){
            return u.getSeller();
        }
        return null;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
