package com.dala.utils;

public class MathUtils {
    public static double round(double number, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(number * factor) / factor;
    }

    private MathUtils() {

    }
}
