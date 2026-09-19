// mapper/CategoryMapper.java
package org.lzhao.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lzhao.blog.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类
     */
    List<Category> findAll();

    /**
     * 根据 ID 查询
     */
    Category findById(@Param("id") Long id);

    /**
     * 新增
     */
    int insert(Category category);

    /**
     * 更新
     */
    int update(Category category);

    /**
     * 删除
     */
    int deleteById(@Param("id") Long id);

    /**
     * 统计分类下的文章数
     */
    Long countArticles(@Param("categoryId") Long categoryId);
}