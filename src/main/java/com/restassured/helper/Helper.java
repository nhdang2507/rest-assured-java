package com.restassured.helper;

import java.util.Random;

public class Helper {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
