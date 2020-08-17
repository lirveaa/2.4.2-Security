package org.javamentor.spring.dao;

import org.javamentor.spring.model.User;

import java.util.List;

public interface DaoUser {
    void createNewUser(User user);
    User readUser(long id);
    void updateUser(User user);
    void deleteUser(long id);
    User getUser(String login);
    List<User> usersList();
}
