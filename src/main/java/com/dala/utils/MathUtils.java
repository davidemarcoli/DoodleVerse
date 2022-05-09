package com.dala.utils;

import java.util.Random;

public class MathUtils {

    private static MathUtils instance;
    Random random = new Random();

    private MathUtils() {

    }

    public static MathUtils getInstance() {
        if (instance == null) {
            instance = new MathUtils();
        }

        return instance;
    }

    public double round(double number, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(number * factor) / factor;
    }

    public int randomMinMax(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
