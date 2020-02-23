package com.mmall.util;

import java.util.Random;

/**
 * 生成密码的工具类
 */
public class PasswordUtil {
    private final static String[] nums ={"0","1","2","3","4","5","6","7","8","9"};

    private final static String[] character={"a","b","c","d","e","f","g","h","i","j","k","l",
            "m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static void main(String[] args) {
        //生成给定位数随机密码
        int i= 10;
        String password = createPassword(i);
        System.out.println("password = " + password);
    }

    public static String createPassword(int i) {
        StringBuffer buffer = new StringBuffer();
        boolean boo = false;
        System.out.println(new Random().nextInt(nums.length));
        for(int j = 0;j<i;j++){
            if(boo){
                //为真，字母
                int length = new Random().nextInt(character.length);
                String english = character[length];
                buffer.append(english);
            }else{
                //为假，数字
                String num = nums[new Random().nextInt(nums.length)];
                buffer.append(num);
            }
            boo = !boo;
        }
        return buffer.toString();
    }
}
