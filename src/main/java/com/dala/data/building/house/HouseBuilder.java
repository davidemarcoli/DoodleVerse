package com.dala.data.building.house;

import com.dala.data.building.size.Size;
import com.dala.data.building.wall.Wall;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class HouseBuilder {
    private String ceilingColor;
    private Size size;
    private Wall wall;

    public HouseBuilder() {

    }

    public HouseBuilder ceilingColor(String ceilingColor) {
        this.ceilingColor = ceilingColor;
        return this;
    }

    public HouseBuilder size(Size size) {
        this.size = size;
        return this;
    }

    public HouseBuilder wall(Wall wall) {
        this.wall = wall;
        return this;
    }

    public House build() {
        return new House(this);
    }
}
