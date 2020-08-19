package org.javamentor.spring.service;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;

import java.util.List;

public interface UserService {
    void createNewUser(User user);

    User readUser(long id);

    void updateUser(User user);

    void deleteUser(long id);

    User getUser(String login);

    List<User> usersList();

    List<Role> rolesList();
}
