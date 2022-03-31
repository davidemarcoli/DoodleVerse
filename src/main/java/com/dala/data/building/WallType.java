package com.dala.data.building;

public enum WallType {
    STONE,
    WOOD,
    BRICK;

    public static WallType getRandomWallType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
