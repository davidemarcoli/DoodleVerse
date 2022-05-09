package com.dala.data.company;

import com.dala.data.building.Building;
import com.dala.data.department.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends Building {
    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    @OneToMany
    private List<Department> departments;
}
