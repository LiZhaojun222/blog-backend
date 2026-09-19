// service/TagService.java
package org.lzhao.blog.service;

import org.lzhao.blog.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    Tag findById(Long id);
    void create(Tag tag);
    void delete(Long id);
    List<Tag> findByArticleId(Long articleId);
    void saveArticleTags(Long articleId, List<Long> tagIds);
}