package com.seleniumdemo.framework.pom.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    public static String generateRandomUsername(){
        Faker faker = new Faker();
        return faker.name().username();
    }
}
