package com.dala.data.company;

import com.dala.data.department.Department;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Company {
    private String companyName;
    private ArrayList<Department> departments;
}
