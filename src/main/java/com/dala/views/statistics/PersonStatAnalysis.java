package com.dala.views.statistics;

import com.dala.data.person.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonStatAnalysis {

    private final ArrayList<Person> persons;

    private final ArrayList<Double> moneyList = new ArrayList<>();


    public PersonStatAnalysis(List<Person> persons) {
        this.persons = new ArrayList<>(persons);

        persons.forEach(person -> {
            moneyList.add(person.getMoney());
        });
    }

    /**
     * Get the lowest number from the moneylist
     * @return the lowest number
     */
    public Number low() {
        return Collections.min(moneyList);
    }

    /**
     * Get the highest number of the moneylist
     * @return the highest number
     */
    public Number high() {
        return Collections.max(moneyList);
    }

    /**
     * Return the quartile from a given percent
     * @param percent the given percent to get the quartile from
     * @return the found number
     */
    public Number quartile(int percent) {
        Collections.sort(moneyList);
        int n = Math.round(moneyList.size() * percent / 100f);
        return moneyList.get(n);
    }

    /**
     * Get the median of the moneylist
     * @return the median
     */
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
