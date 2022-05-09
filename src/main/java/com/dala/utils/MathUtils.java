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

    /**
     * Round the number to the specified decimal
     * @param number the number to round
     * @param decimals the number of decimals
     * @return the rounded number
     */
    public double round(double number, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(number * factor) / factor;
    }

    /**
     * Generate Random Integer between the min and the max value
     * @param min the minimum Value
     * @param max the maximum Value
     * @return the generated Integer
     */
    public int randomMinMax(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    /**
     * Generate Random Double between the min and the max value
     * @param min the minimum Value
     * @param max the maximum Value
     * @return the generated Double
     */
    public double randomMinMax(double min, double max) {
        return random.nextDouble(max - min + 1) + min;
    }

}
