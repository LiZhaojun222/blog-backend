// common/PasswordUtil.java
package org.lzhao.blog.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 加密密码
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 验证密码
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    // 测试用
    public static void main(String[] args) {
        String raw = "123456";
        String encoded = encode(raw);
        System.out.println("加密后：" + encoded);
        System.out.println("验证结果：" + matches(raw, encoded));
    }
}