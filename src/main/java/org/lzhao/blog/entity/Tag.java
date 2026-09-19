// entity/Tag.java
package org.lzhao.blog.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Tag {
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称不能超过 50 个字符")
    private String name;

    private LocalDateTime createTime;
}