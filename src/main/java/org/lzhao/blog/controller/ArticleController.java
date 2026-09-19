package org.lzhao.blog.controller;

import jakarta.validation.Valid;
import org.lzhao.blog.common.Result;
import org.lzhao.blog.entity.Article;
import org.lzhao.blog.entity.dto.ArticleQueryDTO;
import org.lzhao.blog.service.ArticleService;
import org.lzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * 分页查询（公开接口，不需要登录）
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(ArticleQueryDTO query) {
        return Result.success(articleService.findByPage(query));
    }

    /**
     * 查询详情
     */
    @GetMapping("/detail/{id}")
    public Result<Article> detail(@PathVariable Long id) {
        Article article = articleService.findById(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        // ✅ 浏览量 +1
        articleService.incrementViewCount(id);
        // 更新返回的数据
        article.setViewCount(article.getViewCount() + 1);
        return Result.success(article);
    }

    /**
     * 新增（需要登录）
     */
    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody Article article, @RequestAttribute("userId") Long userId) {
        article.setAuthorId(userId);
        articleService.create(article);

        // ✅ 保存标签
        if (article.getTagIds() != null && !article.getTagIds().isEmpty()) {
            tagService.saveArticleTags(article.getId(), article.getTagIds());
        }

        return Result.success("新增成功", null);
    }

    /**
     * 编辑（需要登录）
     */
    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody Article article) {
        articleService.update(article);
        // ✅ 更新标签
        if (article.getTagIds() != null) {
            tagService.saveArticleTags(article.getId(), article.getTagIds());
        }
        return Result.success("编辑成功", null);
    }

    /**
     * 删除（需要登录）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return Result.success("删除成功", null);
    }

    /**
     * 我的文章（需要登录）
     */
    @GetMapping("/my")
    public Result<Map<String, Object>> myArticles(
            ArticleQueryDTO query,
            @RequestAttribute("userId") Long userId) {   // ← 从 Token 取
        Map<String, Object> result = articleService.findByAuthor(query, userId);
        return Result.success(result);
    }
}
