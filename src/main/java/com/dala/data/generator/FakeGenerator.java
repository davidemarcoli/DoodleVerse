package com.dala.data.generator;

import com.dala.data.company.Company;
import com.dala.data.department.Department;
import com.dala.data.department.DepartmentRepository;
import com.dala.data.person.Person;
import com.dala.data.person.PersonRepository;
import com.dala.utils.MathUtils;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@SpringComponent
public class FakeGenerator {
    private static FakeGenerator instance;
    FakeValuesService fakeValuesService = new FakeValuesService(new Locale("de-CH.yml"), new RandomService());
    Faker faker = new Faker(new Locale("de-CH"));
    Random random = new Random();
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private FakeGenerator() {
    }

    @Bean
    public static FakeGenerator getInstance() {
        if (instance == null) {
            instance = new FakeGenerator();
        }
        return instance;
    }

    public ArrayList<Person> generateRandomPersons(int count) {
        ArrayList<Person> persons = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            persons.add(new Person(faker.name().firstName(), faker.name().lastName(), MathUtils.getInstance().round(randomBetween(1000.0, 10000.0), 2)));
        }

        return persons;
    }

    public ArrayList<Company> generateCompanies(int count) {
        ArrayList<Company> companies = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            ArrayList<Department> departments = generateDepartment(10);
            companies.add(new Company(0L, faker.company().name(), departments));
        }

        return companies;

    }

    public ArrayList<Department> generateDepartment(int count) {
        ArrayList<Department> departments = new ArrayList<>();
        List<Person> persons = personRepository.findAll();

        for (int i = 0; i < count; i++) {
            ArrayList<Department> childDepartments = new ArrayList<>();
            List<Person> departmentWorkers = new ArrayList<>();

            for (int personCount = 0; personCount < MathUtils.getInstance().randomMinMax(1, 5); personCount++) {
                Person person = persons.get(MathUtils.getInstance().randomMinMax(0, persons.size() - 1));
                if (!departmentWorkers.contains(person)) {
                    departmentWorkers.add(person);
                }
            }

            if (departmentWorkers.isEmpty()) {
                List<Person> person = personRepository.saveAllAndFlush(generateRandomPersons(MathUtils.getInstance().randomMinMax(1, 5)));
                departmentWorkers.addAll(person);
            }

            for (int j = 0; j < MathUtils.getInstance().randomMinMax(0, 5); j++) {

                departmentWorkers.clear();

                for (int personCount = 0; personCount < MathUtils.getInstance().randomMinMax(1, 5); personCount++) {
                    Person person = persons.get(MathUtils.getInstance().randomMinMax(0, persons.size() - 1));
                    if (!departmentWorkers.contains(person)) {
                        departmentWorkers.add(person);
                    }
                }

                if (departmentWorkers.isEmpty()) {
                    List<Person> person = personRepository.saveAllAndFlush(generateRandomPersons(MathUtils.getInstance().randomMinMax(1, 5)));
                    departmentWorkers.addAll(person);
                }

                Department childDepartment = new Department(0L, faker.commerce().department(), departmentWorkers, null);
                System.out.println(childDepartment);

                childDepartment = departmentRepository.saveAndFlush(childDepartment);
                childDepartments.add(childDepartment);
            }

            Department department = departmentRepository.saveAndFlush(new Department(0L, faker.commerce().department(), null, childDepartments));
            departments.add(department);
        }

        return departments;


    }

    public double randomBetween(double min, double max) {
        return random.nextDouble(max - min + 1) + min;
    }
}
