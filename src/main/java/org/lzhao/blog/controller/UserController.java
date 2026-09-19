package org.lzhao.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.lzhao.blog.common.BusinessException;
import org.lzhao.blog.common.Result;
import org.lzhao.blog.entity.User;
import org.lzhao.blog.entity.dto.LoginDTO;
import org.lzhao.blog.entity.dto.RegisterDTO;
import org.lzhao.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Lzj
 * @create 2026-09-14 18:32
 */
@Tag(name = "用户管理", description = "用户登录、注册、个人信息等接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     */
    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    /**
     * 测试接口
     */
    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("Hello, Spring Boot!");
    }

    // 登录验证
    @Operation(summary = "用户登录", description = "输入用户名密码，返回 Token")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        Map<String, Object> loginResult = userService.login(loginDTO);
        return Result.success("登录成功", loginResult);
    }

    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success("注册成功", null);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/profile")
    public Result<User> profile(@RequestAttribute("userId") Long userId) {
        User user = userService.findById(userId);
        return Result.success(user);
    }

    // controller/UserController.java
    @PutMapping("/nickname")
    public Result<Void> updateNickname(
            @RequestBody Map<String, String> params,
            @RequestAttribute("userId") Long userId) {
        String nickname = params.get("nickname");
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new BusinessException("昵称不能为空");
        }
        userService.updateNickname(userId, nickname);
        return Result.success("修改成功", null);
    }

    @PutMapping("/password")
    public Result<Void> updatePassword(
            @RequestBody Map<String, String> params,
            @RequestAttribute("userId") Long userId) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        if (oldPassword == null || newPassword == null) {
            throw new BusinessException("密码不能为空");
        }

        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success("修改成功", null);
    }
}
