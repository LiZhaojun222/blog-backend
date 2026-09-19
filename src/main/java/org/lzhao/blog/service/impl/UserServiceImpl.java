package org.lzhao.blog.service.impl;

import org.lzhao.blog.common.BusinessException;
import org.lzhao.blog.common.JwtUtil;
import org.lzhao.blog.common.PasswordUtil;
import org.lzhao.blog.entity.User;
import org.lzhao.blog.entity.dto.LoginDTO;
import org.lzhao.blog.entity.dto.RegisterDTO;
import org.lzhao.blog.mapper.UserMapper;
import org.lzhao.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lzj
 * @create 2026-09-14 18:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        // 1. 查询用户
        User user = userMapper.findByUsername(loginDTO.getUsername());

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 2. ✅ 用 BCrypt 验证密码
        if (!PasswordUtil.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 生成 Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 4. 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    @Override
    public User findById(Long id) {
        User user = userMapper.findByUserId(id);
        if(user == null){
            throw new BusinessException("用户不存在，ID错误");
        }
        return user;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // 1. 检查用户名是否已存在
        User existing = userMapper.findByUsername(registerDTO.getUsername());
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }

        // 2. 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(PasswordUtil.encode(registerDTO.getPassword()));  // ← 加密
        user.setNickname(registerDTO.getNickname() != null ?
                registerDTO.getNickname() : registerDTO.getUsername());

        // 3. 保存
        userMapper.insert(user);
    }

    // service/impl/UserServiceImpl.java
    @Override
    public void updateNickname(Long userId, String nickname) {
        userMapper.updateNickname(userId, nickname);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findByUserId(userId);
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        userMapper.updatePassword(userId, PasswordUtil.encode(newPassword));
    }
}
