/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.impl;

import com.infiniteskills.mvc.entity.Typeusers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infiniteskills.mvc.repository.TypeUsersRepository;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Соколов
 */
@Service("jpaTypeuserRepository")
@Transactional
@Repository
public class TypeUsersRepositoryImpl implements TypeUsersRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Typeusers> findAll() {
        return em.createNamedQuery("Typeusers.findAll").getResultList();
    }

    @Override
    public List<Typeusers> findAllWithDetails() {
        return em.createNamedQuery("Typeusers.findAllWithDetail").getResultList();
    }

    @Override
    public Typeusers save(Typeusers typeuser) {
        if (typeuser.getId() == null) {
            em.persist(typeuser);
        } else {
            em.merge(typeuser);
        }
        return typeuser;
    }

    @Override
    public void delete(Typeusers typeuser) {
        Typeusers mergedDep = em.merge(typeuser);
        em.remove(mergedDep);
    }

    @Override
    public Typeusers update(Typeusers typeuser) {
        return em.merge(typeuser);
    }

    @Override
    public Typeusers create(Typeusers typeuser) {
        em.persist(typeuser);
        return typeuser;
    }

    @Override
    public Typeusers getTypeUserByName(String typeUser) {

        TypedQuery<Typeusers> query = em.createQuery("select u from Typeusers u where u.name=?1", Typeusers.class);
        query.setParameter(1, typeUser);
        try {
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }

    }

}
