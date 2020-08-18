package org.javamentor.spring.dao;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaoUser{
    void createNewUser(User user);
    User readUser(long id);
    void updateUser(User user);
    void deleteUser(long id);
    User getUser(String login);
    List<User> usersList();
    List<Role> rolesList();
}
