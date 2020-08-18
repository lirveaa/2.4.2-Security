package org.javamentor.spring.repository;

import org.javamentor.spring.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    @Autowired
    EntityManager emR;

    @Override
    public List<Role> rolesList() {
        TypedQuery<Role> query =
                emR.createQuery("SELECT u FROM Role u", Role.class);
        return query.getResultList();
    }
}
