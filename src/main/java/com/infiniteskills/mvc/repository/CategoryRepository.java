/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.repository;

import com.infiniteskills.mvc.entity.Category;
import java.util.List;


/**
 *
 * @author Соколов
 */
public interface CategoryRepository  {
    List<Category> findAll();

    Category update(Category category);

    Category create(Category category);

    List<Category> findAllWithDetails();

    Category save(Category category);

    void delete(Category category);
}
