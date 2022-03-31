package com.dala.data.building;

import com.dala.data.company.Company;
import lombok.Data;

import java.nio.file.Path;
import java.util.ArrayList;

@Data
public class IndustryFacility implements Building {

    private ArrayList<Company> companies;

    @Override
    public Path getPictureOfBuilding(boolean isNight) {
        return Path.of("images/empty-plant.png");
    }

}
