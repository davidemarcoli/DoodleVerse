package com.dala.data.building.house;

import com.dala.data.building.Building;
import com.dala.data.building.size.Size;
import com.dala.data.building.wall.Wall;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class House extends Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ceilingColor;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Wall wall;

    public House(HouseBuilder houseBuilder) {
        this.ceilingColor = houseBuilder.getCeilingColor();
        this.size = houseBuilder.getSize();
        this.wall = houseBuilder.getWall();
    }
}
