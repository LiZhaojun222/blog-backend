// service/impl/CategoryServiceImpl.java
package org.lzhao.blog.service.impl;

import org.lzhao.blog.common.BusinessException;
import org.lzhao.blog.entity.Category;
import org.lzhao.blog.mapper.CategoryMapper;
import org.lzhao.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void create(Category category) {
        if (category.getSort() == null) {
            category.setSort(0);
        }
        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        // 检查分类下是否有文章
        Long count = categoryMapper.countArticles(id);
        if (count > 0) {
            throw new BusinessException("该分类下还有 " + count + " 篇文章，无法删除");
        }
        categoryMapper.deleteById(id);
    }
}