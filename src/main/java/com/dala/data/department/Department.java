package com.dala.data.department;

import com.dala.data.person.Person;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    @ManyToMany
    private List<Person> workers;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Department> childDepartments;
}
