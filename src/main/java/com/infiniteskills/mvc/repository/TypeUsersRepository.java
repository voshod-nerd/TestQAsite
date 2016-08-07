/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.repository;


import com.infiniteskills.mvc.entity.Typeusers;
import java.util.List;


/**
 *
 * @author Юыху
 */
public interface TypeUsersRepository   {
    List<Typeusers> findAll();

    Typeusers update(Typeusers typeuser);

    Typeusers create(Typeusers typeuser);

    List<Typeusers> findAllWithDetails();

    Typeusers save(Typeusers typeuser);

    void delete(Typeusers typeuser);
    
     Typeusers getTypeUserByName(String typeUser);
    
}
