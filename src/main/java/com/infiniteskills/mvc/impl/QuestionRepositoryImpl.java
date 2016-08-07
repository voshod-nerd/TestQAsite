/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.impl;

import com.infiniteskills.mvc.entity.Questions;
import com.infiniteskills.mvc.repository.QuestionRepository;
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
@Service("jpaQuestionRepository")
@Transactional
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Questions> findAll() {
        return em.createNamedQuery("Questions.findAll").getResultList();
    }

    @Override
    public List<Questions> findAllWithDetails() {
        return em.createNamedQuery("Questions.findAllWithDetail").getResultList();
    }

    @Override
    public Questions save(Questions question) {
        if (question.getId() == null) {
            em.persist(question);
        } else {
            em.merge(question);
        }
        return question;
    }

    @Override
    public void delete(Questions question) {
        Questions mergedDep = em.merge(question);
        em.remove(mergedDep);
    }

    @Override
    public Questions update(Questions question) {
        return em.merge(question);
    }

    @Override
    public Questions create(Questions question) {
        em.persist(question);
        return question;
    }

    @Override
    public Questions getById(Integer id) {
        TypedQuery<Questions> query = em.createQuery("select u from Questions u where u.id=?1", Questions.class);
        query.setParameter(1, id);
        try {
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Questions> getQuestionsByCategory(Integer idcategory) {
        TypedQuery<Questions> query = em.createQuery("select u from Questions u where u.idcategory.id=?1", Questions.class);
        query.setParameter(1, idcategory);
        try {
            return query.getResultList();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }

}
