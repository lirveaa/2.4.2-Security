package org.javamentor.spring.service;

import org.javamentor.spring.dao.DaoUser;
import org.javamentor.spring.dao.DaoUserImpl;
import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional // Warning
public class UserServiceImpl implements UserService {

    @Autowired
    private DaoUser daoUser;

    @Transactional
    @Override
    public void createNewUser(User user) {
        if (user.getRoles().size() == 0) {
            System.out.println("\nChecking roles in the createNewUser(User user).method in the UserServiceImpl.class");
            System.out.println("adding default ROLE_USER role because null roles received = ");
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        daoUser.createNewUser(user);
    }

    @Transactional
    @Override
    public User readUser(long id) {
        return daoUser.readUser(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        daoUser.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        daoUser.deleteUser(id);
    }

    @Transactional
    @Override
    public User getUser(String login) {
        return daoUser.getUser(login);
    }

    @Transactional
    @Override
    public List<User> usersList() {
        return daoUser.usersList();
    }

    @Transactional
    @Override
    public List<Role> rolesList() {
        return daoUser.rolesList();
    }
}
