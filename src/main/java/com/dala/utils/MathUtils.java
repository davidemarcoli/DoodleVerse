package com.dala.utils;

import java.util.Random;

public class MathUtils {
    
    Random random = new Random();
    
    public double round(double number, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(number * factor) / factor;
    }

    public int randomMinMax(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
    
    private static MathUtils instance;
    
    public static MathUtils getInstance() {
        if (instance == null) {
            instance = new MathUtils();
        }

        return instance;
    }

    private MathUtils() {

    }
}
