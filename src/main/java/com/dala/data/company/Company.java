package com.dala.data.company;

import com.dala.data.department.Department;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    @OneToMany
    private List<Department> departments;
}
