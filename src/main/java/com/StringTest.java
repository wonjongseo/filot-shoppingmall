package com;

public class StringTest {
    public static void main(String[] args) {
        String url = "https://filotshopping.s3.ap-northeast-2.amazonaws.com/top/000017.jpg";
        int i = url.lastIndexOf(".com");
        System.out.println("i = " + i);

        String substring = url.substring(i + 5);
        System.out.println("substring = " + substring);
    }
}
