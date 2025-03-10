package com.goods.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // 加密密码
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // 验证密码
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public static void main(String[] args) {
        String originalPassword = "147258qa@";
        String hashedPassword = PasswordUtil.hashPassword(originalPassword);

        // 存储 hashedPassword 到数据库
        System.out.println(hashedPassword);
//        // 登录验证时
//        String inputPassword = "222"; // 用户登录时输入的密码
//        boolean isPasswordCorrect = PasswordUtil.verifyPassword(inputPassword, hashedPassword);
//
//        if (isPasswordCorrect) {222
//            System.out.println("Password is correct.");
//        } else {
//            System.out.println("Password is incorrect.");
//        }
    }
}

