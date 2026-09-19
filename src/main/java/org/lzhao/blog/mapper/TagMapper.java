// mapper/TagMapper.java
package org.lzhao.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lzhao.blog.entity.Tag;

import java.util.List;

@Mapper
public interface TagMapper {

    /**
     * 查询所有标签
     */
    List<Tag> findAll();

    /**
     * 根据 ID 查询
     */
    Tag findById(@Param("id") Long id);

    /**
     * 根据名称查询
     */
    Tag findByName(@Param("name") String name);

    /**
     * 新增标签
     */
    int insert(Tag tag);

    /**
     * 删除标签
     */
    int deleteById(@Param("id") Long id);

    /**
     * 查询某篇文章的所有标签
     */
    List<Tag> findByArticleId(@Param("articleId") Long articleId);

    /**
     * 给文章添加标签
     */
    int insertArticleTag(@Param("articleId") Long articleId,
                         @Param("tagId") Long tagId);

    /**
     * 删除文章的所有标签
     */
    int deleteArticleTags(@Param("articleId") Long articleId);
}