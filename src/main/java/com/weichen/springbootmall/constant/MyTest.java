package com.weichen.springbootmall.constant;

public class MyTest {

    public static void main(String[] args) {
        ProductCategory category = ProductCategory.FOOD;
        String s = category.name();   //將enum的值轉成string
        System.out.println(s); //food

        String s2 = "CAR";

        // valueOf = 查詢enum的值
        ProductCategory category2 = ProductCategory.valueOf(s2);

    }
}
