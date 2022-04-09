package com.dala.data.building.industryfacility;

import com.dala.data.building.Building;
import com.dala.data.company.Company;
import lombok.*;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndustryFacility extends Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Company> companies;

    @Override
    public Path getPictureOfBuilding(boolean isNight) {
        return Path.of("images/empty-plant.png");
    }

}
