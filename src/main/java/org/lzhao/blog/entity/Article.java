// entity/Article.java
package org.lzhao.blog.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Article {
    private Long id;

    @Schema(description = "标题", example = "Spring Boot 入门")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "内容", example = "这是内容...")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(description = "摘要", example = "Spring Boot 快速上手")
    @Size(max = 500, message = "摘要不能超过 500 个字符")
    private String summary;

    @Schema(description = "封面图 URL")
    private String coverImage;

    @Schema(description = "分类 ID", example = "1")
    private Long categoryId;

    @Schema(description = "作者 ID", example = "1")
    private Long authorId;

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "状态：1=发布，0=草稿", example = "1")
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<Tag> tags;
    private List<Long> tagIds;
}