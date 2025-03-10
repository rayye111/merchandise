package com.goods.utils;

import java.util.Random;

public class GenerateVerificationCodeUtil {

    public static String generateVerificationCode(int length){
        // 生成5位随机验证码
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 生成一个0到9的数字
        }
        return sb.toString();
    }
}
