package com.dala.data.building;

public enum CeilingType {
    RED,
    BLUE,
    YELLOW;

    public static CeilingType getRandomType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
