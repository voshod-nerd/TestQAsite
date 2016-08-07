/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.impl;

import com.infiniteskills.mvc.entity.Category;

import com.infiniteskills.mvc.repository.CategoryRepository;

import java.util.List;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Юыху
 */

@Service("jpaCategoryRepository")
@Transactional
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
     @PersistenceContext
    private EntityManager em;
     
     @Override
    public List<Category> findAll() {
       return em.createNamedQuery("Category.findAll").getResultList();
    }
    
    @Override
     public List<Category> findAllWithDetails() {
        return em.createNamedQuery("Category.findAllWithDetail").getResultList();
    }

     
    @Override 
    public Category save(Category category) {
          if (category.getId() == null) {
            em.persist(category);
        } else {
            em.merge(category);
        }
        return category;
    }
    
    @Override
    public void delete(Category category) {
          Category mergedDep = em.merge(category);
        em.remove(mergedDep);
    }
    
    @Override
    public Category update(Category category) {
         return em.merge(category);
    }
    
    @Override
    public Category create(Category category) {
       em.persist(category);
        return category;
    }
    
      
    
    
}
