// controller/TagController.java
package org.lzhao.blog.controller;

import jakarta.validation.Valid;
import org.lzhao.blog.common.Result;
import org.lzhao.blog.entity.Tag;
import org.lzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询所有标签（公开）
     */
    @GetMapping("/list")
    public Result<List<Tag>> list() {
        return Result.success(tagService.findAll());
    }

    /**
     * 新增标签（需要登录）
     */
    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody Tag tag) {
        tagService.create(tag);
        return Result.success("新增成功", null);
    }

    /**
     * 删除标签（需要登录）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return Result.success("删除成功", null);
    }
}