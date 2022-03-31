package com.dala.data.city;

import com.dala.data.building.Building;
import lombok.Data;

import java.util.ArrayList;

@Data
public class City {
    private String cityName;
    private ArrayList<Building> buildings;
}
