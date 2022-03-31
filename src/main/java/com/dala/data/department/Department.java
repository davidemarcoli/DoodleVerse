package com.dala.data.department;

import com.dala.data.person.Person;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Department {
    private String departmentName;
    private ArrayList<Person> workers;
    private ArrayList<Department> childDepartments;
}
