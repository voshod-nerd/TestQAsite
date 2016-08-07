/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.impl;

import com.infiniteskills.mvc.entity.Answers;
import com.infiniteskills.mvc.entity.Questions;
import com.infiniteskills.mvc.repository.AnswersRepository;

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
 * @author Соколов
 */
@Service("jpaAnswersRepository")
@Transactional
@Repository
public class AnswersRepositoryImpl implements AnswersRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Answers> findAll() {
        return em.createNamedQuery("Answers.findAll").getResultList();
    }

    @Override
    public List<Answers> findAllWithDetails() {
        return em.createNamedQuery("Answers.findAllWithDetail").getResultList();
    }

    @Override
    public Answers save(Answers answer) {
        if (answer.getId() == null) {
            em.persist(answer);
        } else {
            em.merge(answer);
        }
        return answer;
    }

    @Override
    public void delete(Answers answer) {
        Answers mergedDep = em.merge(answer);
        em.remove(mergedDep);
    }

    @Override
    public Answers update(Answers answer) {
        return em.merge(answer);
    }

    @Override
    public Answers create(Answers answer) {
        em.persist(answer);
        return answer;
    }

    @Override
    public List<Answers> getByQuestion(Questions question) {
        TypedQuery<Answers> query = em.createQuery("select u from Answers u where u.idquestion.id=?1", Answers.class);
        query.setParameter(1, question.getId());
        try {
            return query.getResultList();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }

    }

}
