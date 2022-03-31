package com.dala.data;

import com.dala.data.person.Person;
import com.dala.utils.MathUtils;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class DataManager {
    public ArrayList<Person> persons;

    FakeValuesService fakeValuesService = new FakeValuesService(new Locale("de-CH.yml"), new RandomService());
    Faker faker = new Faker(new Locale("de-CH"));
    Random random = new Random();

    public void generateRandomPersons(int count) {
        for (int i = 0; i < count; i++) {
            persons.add(new Person(faker.name().firstName(), faker.name().lastName(), MathUtils.round(randomBetween(1000.0, 10000.0), 2)));
        }
    }

    private double randomBetween(double min, double max) {
        return random.nextDouble(max - min + 1) + min;
    }

    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        persons = new ArrayList<>();
    }
}
