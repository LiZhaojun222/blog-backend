package org.lzhao.blog.service;

import org.lzhao.blog.entity.Article;
import org.lzhao.blog.entity.dto.ArticleQueryDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String, Object> findByPage(ArticleQueryDTO query);
    Map<String, Object> findByAuthor(ArticleQueryDTO query, Long authorId);  // ← 新增
    Article findById(Long id);
    void create(Article article);
    void update(Article article);
    void delete(Long id);
    void incrementViewCount(Long id);
    void saveTags(Long articleId, List<Long> tagIds);  // ← 新增
}
