package org.lzhao.blog.service;
import org.lzhao.blog.entity.User;
import org.lzhao.blog.entity.dto.LoginDTO;
import org.lzhao.blog.entity.dto.RegisterDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Lzj
 * @create 2026-09-14 18:30
 */


public interface UserService {
    List<User> findAll();

    /**
     * 登录
     */
    Map<String, Object> login(LoginDTO loginDTO);

    User findById(Long id);

    void register(RegisterDTO registerDTO);

    void updateNickname(Long userId, String nickname);

    void updatePassword(Long userId, String oldPassword, String newPassword);
}