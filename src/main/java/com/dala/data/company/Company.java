package com.dala.data.company;

import com.dala.data.department.Department;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Department> departments;
}
