package org.lzhao.blog.entity;

/**
 * @author Lzj
 * @create 2026-09-14 18:29
 */
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
