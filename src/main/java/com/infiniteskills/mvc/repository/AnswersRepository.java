/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.repository;

import com.infiniteskills.mvc.entity.Answers;
import com.infiniteskills.mvc.entity.Questions;
import java.util.List;


/**
 *
 * @author Юыху
 */
public interface AnswersRepository  {
    List<Answers> findAll();

    Answers update(Answers answer);

    Answers create(Answers answer);

    List<Answers> findAllWithDetails();

    Answers save(Answers answer);

    void delete(Answers answer); 
    List<Answers> getByQuestion(Questions question);
}
