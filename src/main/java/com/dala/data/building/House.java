package com.dala.data.building;

import lombok.Data;

import java.nio.file.Path;

@Data
public class House implements Building {



    @Override
    public Path getPictureOfBuilding(boolean isNight) {
        return Path.of("images/empty-plant.png");
    }
}
