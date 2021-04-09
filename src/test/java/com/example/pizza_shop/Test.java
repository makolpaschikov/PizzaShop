package com.example.pizza_shop;

public class Test {
    @org.junit.jupiter.api.Test
    void pathToRes() {
        String path = getClass()
                .getClassLoader()
                .getResource("static/image")
                .getFile();
        System.out.println(path);
    }

    @org.junit.jupiter.api.Test
    void sysPath() {
        String path = System.getProperty("user.dir");
        System.out.println(path);
    }
}
