package com.dala.data.building;

public enum Size {
    SMALL,
    WIDE,
    EXTRA_WIDE;

    public static Size getRandomType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
