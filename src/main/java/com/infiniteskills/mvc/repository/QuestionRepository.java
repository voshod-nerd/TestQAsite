/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.repository;


import com.infiniteskills.mvc.entity.Questions;
import java.util.List;


/**
 *
 * @author Соколов
 */
public interface QuestionRepository  {
   List<Questions> findAll();

   Questions update(Questions question);

   Questions create(Questions question);

   List<Questions> findAllWithDetails();

   Questions save(Questions question);

   void delete(Questions question); 
   
   Questions getById(Integer id);
   
   List<Questions> getQuestionsByCategory(Integer idcategory);
    
}
