package com.dala.views.statistics;

import com.dala.data.person.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonStatAnalysis {

    private ArrayList<Person> persons;

    private ArrayList<Double> moneyList = new ArrayList<>();


    public PersonStatAnalysis(List<Person> persons) {
        this.persons = new ArrayList<>(persons);

        persons.forEach(person -> {
            moneyList.add(person.getMoney());
        });
    }

    public Number low() {
//        Collections.sort(moneyList);
//        return moneyList.get(0);

        return Collections.min(moneyList);
    }

    public Number high() {
        return Collections.max(moneyList);
    }

    public Number quartile(int percent) {
        Collections.sort(moneyList);
        int n = Math.round(moneyList.size() * percent / 100f);
        return moneyList.get(n);
    }

    public Number median() {
        Collections.sort(moneyList);

        double median;

        if (moneyList.size() % 2 == 0)
            median = ((double) moneyList.get(moneyList.size() / 2) + moneyList.get(moneyList.size() / 2 - 1)) / 2;
        else
            median = moneyList.get(moneyList.size() / 2);

        return median;
    }
}
