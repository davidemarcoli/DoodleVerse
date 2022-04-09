package com.dala.data.building.house;

import com.dala.data.building.Building;
import com.dala.data.building.ceiling.Ceiling;
import com.dala.data.building.size.Size;
import com.dala.data.building.wall.Wall;
import lombok.*;

import javax.persistence.*;
import java.nio.file.Path;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class House extends Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ceiling ceiling;

    @ManyToOne
    private Size size;

    @ManyToOne
    private Wall wall;


    @Override
    public Path getPictureOfBuilding(boolean isNight) {
        return Path.of("images/empty-plant.png");
    }
}
