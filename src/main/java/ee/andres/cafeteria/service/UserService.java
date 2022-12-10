package ee.andres.cafeteria.service;

import ee.andres.cafeteria.pojo.Seller;

import java.security.Principal;

public interface UserService {
    Seller getByLoggedInUser(Principal loggedInUser);
}
