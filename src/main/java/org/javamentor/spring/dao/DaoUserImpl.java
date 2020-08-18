package org.javamentor.spring.dao;

import org.javamentor.spring.model.Role;
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

        Set<Role> eSet = user.getRoles();
//        for (Role theRole: eSet) {
//            System.out.println("Try to add Role to DB..." + theRole);
//            em.persist(theRole);
//            System.out.println("Try to add Role to DB ..." + theRole + "success!");
//        }

        if (eSet != null) {
            user.printAllRoles();
//            System.out.println("Roles :=  " + eSet + " in List: ");
//            eSet.forEach(System.out::println);
            System.out.println(("... user has roles.... user.getRoles().size Detected " + eSet.size() + " roles"));
        } else {
            System.out.println("user don't have a roles. I add ROLE_USER");
            Set<Role> defSet = new HashSet<>();
            Role aRole = em.find(Role.class, 1L);
            defSet.add(aRole);
            user.setRoles(defSet);
            System.out.println("Trying to add renewed  " + user);
        }

        System.out.println("saving ... (merge)");
          user = em.merge(user);
     //   em.persist(user);
        //em.merge(aRole);

        System.out.println("User " + user + " добавлен в базу данных");
       // em.clear();
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

//        assert aUser != null;
//        Set<Role> roles = aUser.getRoles();
//
//            Set<Role> rolesToMerge = new HashSet<>();
//
//        for (Role aRole: roles) {
//            System.out.println("Detected Roles: " + aRole);
//            Set<User> userSet = aRole.getUsers();
//
//            for (User thisUser: userSet) {
//                if (thisUser.getId() == aUser.getId()) {
//                    Role inRole = new Role(aRole.getId(), aRole.getRole());
//                    rolesToMerge.add(inRole);
//                    System.out.println(" ---- Detected and added this  Role: " + inRole);
//                }
//            }
//        }

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
