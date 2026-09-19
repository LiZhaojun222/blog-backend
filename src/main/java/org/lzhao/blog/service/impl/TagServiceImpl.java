// service/impl/TagServiceImpl.java
package org.lzhao.blog.service.impl;

import org.lzhao.blog.common.BusinessException;
import org.lzhao.blog.entity.Tag;
import org.lzhao.blog.mapper.TagMapper;
import org.lzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findAll() {
        return tagMapper.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagMapper.findById(id);
    }

    @Override
    public void create(Tag tag) {
        // 检查标签是否已存在
        Tag existing = tagMapper.findByName(tag.getName());
        if (existing != null) {
            throw new BusinessException("标签已存在");
        }
        tagMapper.insert(tag);
    }

    @Override
    public void delete(Long id) {
        tagMapper.deleteById(id);
    }

    @Override
    public List<Tag> findByArticleId(Long articleId) {
        return tagMapper.findByArticleId(articleId);
    }

    @Override
    @Transactional  // ← 事务：要么全成功，要么全失败
    public void saveArticleTags(Long articleId, List<Long> tagIds) {
        // 1. 先删除文章的所有旧标签
        tagMapper.deleteArticleTags(articleId);

        // 2. 再添加新标签
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long tagId : tagIds) {
                tagMapper.insertArticleTag(articleId, tagId);
            }
        }
    }
}