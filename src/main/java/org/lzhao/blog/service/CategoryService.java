// service/CategoryService.java
package org.lzhao.blog.service;

import org.lzhao.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    void create(Category category);
    void update(Category category);
    void delete(Long id);
}