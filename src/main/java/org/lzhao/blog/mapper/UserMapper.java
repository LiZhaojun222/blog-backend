package org.lzhao.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.lzhao.blog.entity.User;
/**
 * @author Lzj
 * @create 2026-09-14 18:29
 */


import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();


    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByUserId(@Param("id") long id);

    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // mapper/UserMapper.java
    @Insert("INSERT INTO user (username, password, nickname) " +
            "VALUES (#{username}, #{password}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET nickname = #{nickname} WHERE id = #{id}")
    int updateNickname(@Param("id") Long id, @Param("nickname") String nickname);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);
}