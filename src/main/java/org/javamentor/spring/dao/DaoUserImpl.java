package org.javamentor.spring.dao;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.RoleEnum;
import org.javamentor.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

        System.out.println("\n ==== createNewUser method in DaoUserImpl");
        System.out.println("Trying to add " + user);
        System.out.println("Roles :=  " + user.getRoles());

        Set<Role> eSet = null;
        if (user.getRoles().size() != 0) {
            System.out.println(("... user.getRoles().size Detected as not 0 ...."));
            eSet = user.getRoles();
        } else {
            Role defaultRole = new Role(RoleEnum.ROLE_USER);
            eSet.add(defaultRole);
            user.setRoles(eSet);
        }

        em.persist(user);
        for (Role aRole: eSet) {
            em.persist(aRole);
            System.out.println("== Added role:" + aRole + " добавлен в базу данных");
        }

//        em.persist(user.getRoles());
        System.out.println("User " + user + " добавлен в базу данных");
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
            return query.getResultList().stream().findAny().orElse(null);
        }

    @Override
    public List<User> usersList() {
        TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}
