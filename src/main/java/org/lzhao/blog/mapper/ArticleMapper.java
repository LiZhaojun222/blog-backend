package org.lzhao.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.lzhao.blog.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    List<Article> findByPage(@Param("keyword") String keyword,
                             @Param("status") Integer status,
                             @Param("categoryId") Long categoryId,   // ← 新增
                             @Param("sortBy") String sortBy,
                             @Param("order") String order,
                             @Param("offset") Integer offset,
                             @Param("limit") Integer limit);

    Long countByCondition(@Param("keyword") String keyword,
                          @Param("status") Integer status,
                          @Param("categoryId") Long categoryId);

    Article findById(@Param("id") Long id);

    int insert(Article article);

    int update(Article article);

    int deleteById(@Param("id") Long id);

    int incrementViewCount(@Param("id") Long id);

    /**
     * 查询某个作者的文章（分页）
     */
    List<Article> findByAuthor(@Param("authorId") Long authorId,
                               @Param("offset") Integer offset,
                               @Param("limit") Integer limit);

    /**
     * 统计某个作者的文章总数
     */
    Long countByAuthor(@Param("authorId") Long authorId);
}