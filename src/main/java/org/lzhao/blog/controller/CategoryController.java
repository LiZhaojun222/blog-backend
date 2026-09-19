// controller/CategoryController.java
package org.lzhao.blog.controller;

import jakarta.validation.Valid;
import org.lzhao.blog.common.Result;
import org.lzhao.blog.entity.Category;
import org.lzhao.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询所有分类（公开）
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.findAll());
    }

    /**
     * 新增分类（需要登录）
     */
    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody Category category) {
        categoryService.create(category);
        return Result.success("新增成功", null);
    }

    /**
     * 编辑分类（需要登录）
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success("编辑成功", null);
    }

    /**
     * 删除分类（需要登录）
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success("删除成功", null);
    }
}