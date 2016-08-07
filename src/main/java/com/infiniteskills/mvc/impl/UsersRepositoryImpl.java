/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.impl;

import com.infiniteskills.mvc.entity.Users;
import com.infiniteskills.mvc.repository.UsersRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Юыху
 */
@Service("jpaUsersRepository")
@Transactional
@Repository
public class UsersRepositoryImpl implements UsersRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Users> findAll() {
        return em.createNamedQuery("Users.findAll").getResultList();
    }

    @Override
    public List<Users> findAllWithDetails() {
        return em.createNamedQuery("Users.findAllWithDetail").getResultList();
    }

    @Override
    public Users save(Users user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }

    @Override
    public void delete(Users user) {
        Users mergedDep = em.merge(user);
        em.remove(mergedDep);
    }

    @Override
    public Users create(Users user) {
        em.persist(user);
        return user;
    }

    @Override
    public Users update(Users user) {
        return em.merge(user);
    }

    public Users find(Users user) {
        return em.find(Users.class, user.getId());
    }
    
    @Override
    public Users findUserByLogin(String username){
          
		TypedQuery<Users> query = em.createQuery("select u from Users u where u.username=?1", Users.class);
		query.setParameter(1, username);
		try {
			return query.getSingleResult();
		} catch (NonUniqueResultException | NoResultException e) {
			return null;
		}
    
    }

    
    
    
}
