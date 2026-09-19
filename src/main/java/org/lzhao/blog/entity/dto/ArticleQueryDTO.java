package org.lzhao.blog.entity.dto;

/**
 * @author Lzj
 * @create 2026-09-15 16:07
 */
import lombok.Data;

@Data
public class ArticleQueryDTO {
    private String keyword;      // 搜索关键词
    private Integer status;      // 状态：1=发布，0=草稿
    private Long categoryId;      // 分类筛选
    private Integer page = 1;    // 页码，默认第 1 页
    private Integer limit = 10;  // 每页条数，默认 10 条
    private String sortBy = "create_time";  // ← 排序字段
    private String order = "desc";          // ← 排序方向（asc/desc）
}
