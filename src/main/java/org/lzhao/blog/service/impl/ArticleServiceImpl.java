package org.lzhao.blog.service.impl;

import org.lzhao.blog.entity.Article;
import org.lzhao.blog.entity.dto.ArticleQueryDTO;
import org.lzhao.blog.mapper.ArticleMapper;
import org.lzhao.blog.service.ArticleService;
import org.lzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Override
    public Map<String, Object> findByPage(ArticleQueryDTO query) {
        // 计算 offset
        int offset = (query.getPage() - 1) * query.getLimit();

        // 查询列表
        List<Article> list = articleMapper.findByPage(
                query.getKeyword(),
                query.getStatus(),
                query.getCategoryId(),    // ← 新增
                query.getSortBy(),
                query.getOrder(),
                offset,
                query.getLimit()
        );

        // 查询总数
        Long total = articleMapper.countByCondition(
                query.getKeyword(),
                query.getStatus(),
                query.getCategoryId()
        );

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", query.getPage());
        result.put("limit", query.getLimit());
        return result;
    }

    @Override
    public Map<String, Object> findByAuthor(ArticleQueryDTO query, Long authorId) {
        int offset = (query.getPage() - 1) * query.getLimit();

        List<Article> list = articleMapper.findByAuthor(
                authorId,
                offset,
                query.getLimit()
        );

        Long total = articleMapper.countByAuthor(authorId);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", query.getPage());
        result.put("limit", query.getLimit());
        return result;
    }

    @Override
    public Article findById(Long id) {
        Article article = articleMapper.findById(id);
        if (article != null) {
            // ✅ 查询文章的标签
            article.setTags(tagService.findByArticleId(id));
        }
        return article;
    }

    @Override
    @Transactional  // ← 加事务
    public void create(Article article) {
        if (article.getStatus() == null) {
            article.setStatus(1);  // 默认发布
        }
        articleMapper.insert(article);
    }

    @Override
    @Transactional  // ← 加事务
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void delete(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    public void incrementViewCount(Long id) {
        articleMapper.incrementViewCount(id);
    }

    @Override
    public void saveTags(Long articleId, List<Long> tagIds) {
        tagService.saveArticleTags(articleId, tagIds);
    }
}