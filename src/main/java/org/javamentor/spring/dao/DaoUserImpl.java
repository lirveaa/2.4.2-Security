package org.javamentor.spring.dao;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class DaoUserImpl implements DaoUser {

    @PersistenceContext
    @Autowired
    EntityManager em;

    @Override
    public void createNewUser(User user) {

        System.out.println("--- createNewUser method in DaoUserImpl");
        System.out.println("Trying to add " + user);
        Set<Role> eSet = user.getRoles();

        if (eSet.size() == 0) {
            System.out.println("!!!!! WARNING !!!! user don't have a roles. I add ROLE_USER");
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        System.out.println(("Detected " + eSet.size() + " roles"));
        user.printAllRoles();
        user = em.merge(user);
        System.out.println(user + " добавлен в базу данных");
    }

    @Override
    public User readUser(long id) {
        return (User) em.createQuery("from User where id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = readUser(id);
        em.remove(user);
    }

    @Override
    public User getUser(String login) {
            TypedQuery<User> query = em.createQuery(
                    "select u from User u where u.login = :login",
                    User.class );
                query.setParameter("login", login);
            User aUser = query.getResultList().stream().findAny().orElse(null);
            return aUser ;
        }

    @Override
    public List<User> usersList() {
        TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

//    @SuppressWarnings("unchecked")
    @Override
    public List<Role> rolesList() {
        TypedQuery<Role> query =
                em.createQuery("SELECT u FROM Role u", Role.class);
        return query.getResultList();
    }

}
